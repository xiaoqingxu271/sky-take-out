package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author chun0
* @since 2025/7/8 13:59
* @version 1.0
*/
@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Autowired
    private DishMapper dishMapper;

    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuerySetmeal(SetmealPageQueryDTO setmealPageQueryDTO) {
        // 开启分页
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());

        // 调用Mapper层分页查询
        Page<Setmeal> page = setmealMapper.pageQuerySetmeal(setmealPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 新增套餐
     * @param setmealDTO
     */
    @Override
    public void saveSetmeal(SetmealDTO setmealDTO) {
        // 封装Setmeal对象
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        // 调用Mapper新增套餐
        setmealMapper.saveSetmeal(setmeal);

        // 取出关联的菜品数据
        Long setmealId = setmeal.getId();
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && setmealDishes.size() > 0) {
            for (SetmealDish setmealDish : setmealDishes) {
                setmealDish.setSetmealId(setmealId);
            }
            setmealDishMapper.saveSetmealDish(setmealDishes);
        }

    }

    /**
     * 启售停售套餐
     * @param status
     * @param id
     */
    @Override
    public void startOrStopSetmeal(Integer status, Long id) {
        // 根据套餐的id查询出菜品的id集合
        List<Long> dishIds = setmealDishMapper.getDishIdsBySetmealId(id);
        // 根据菜品的id查询出菜品的起售状态
        List<Dish> dishes = new ArrayList<>();
        for (Long dishId : dishIds) {
            Dish dish = dishMapper.getDishById(dishId);
            dishes.add(dish);
        }
        // 遍历菜品集合，判断是否有起售中的菜品
        for (Dish dish : dishes) {
            if (dish.getStatus() == 0) {
                throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
            }
        }

        // 套餐起售停售
        Setmeal setmeal = Setmeal.builder()
                .id(id)
                .status(status)
                .build();
        setmealMapper.startOrStopSetmeal(setmeal);
    }

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @Override
    public SetmealVO getSetmealById(Long id) {
        // 先查询套餐数据
        Setmeal setmeal = setmealMapper.getSetmealById(id);
        // 根据套餐id查询关联的菜品
        List<SetmealDish> setmealDishes = setmealDishMapper.getSetmealDishBySetmealId(id);
        // 封装SetmealVO对象并返回
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    /**
     * 修改套餐
     * @param setmealDTO
     */
    @Override
    public void update(SetmealDTO setmealDTO) {
        // 先修改套餐数据
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.update(setmeal);
        // 修改套餐关联的菜品数据
        // 先删除套餐关联的菜品数据
        Long setmealId = setmealDTO.getId();
        setmealDishMapper.deleteBySetmealId(setmealId);
        // 在添加套餐关联的菜品数据
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && setmealDishes.size() > 0) {
            for (SetmealDish setmealDish : setmealDishes) {
                setmealDish.setSetmealId(setmealId);
            }
            setmealDishMapper.saveSetmealDish(setmealDishes);
        }
    }

    /**
     * 批量删除套餐
     * @param ids
     */
    @Override
    public void deleteSetmeals(String ids) {
        List<Long> idList = new ArrayList<>();
        String[] split = ids.split(",");
        for (String id : split) {
            idList.add(Long.valueOf(id));
        }
        // 查询套餐的状态
        for (Long id : idList) {
            Setmeal setmeal = setmealMapper.getSetmealById(id);
            if (setmeal.getStatus() == 1) {
                // 如果是起售中的套餐，则不能删除
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }
        // 删除套餐数据
        setmealMapper.deleteSetmeals(idList);
        setmealDishMapper.deleteBySetmealIds(idList);
    }

    /**
     * 根据分类id查询套餐
     * @param categoryId
     * @return
     */
    @Override
    public List<Setmeal> list(Long categoryId) {
        List<Setmeal> setmeals = setmealMapper.getSetmealByCategoryId(categoryId);
        List<Setmeal> setmealList = new ArrayList<>();
        for (Setmeal setmeal : setmeals) {
            if (setmeal.getStatus() == 1) {
                setmealList.add(setmeal);
            }
        }
        return setmealList;
    }

    /**
     * 根据套餐id查询包含的菜品数据
     * @param id
     * @return
     */
    @Override
    public List<DishItemVO> getDishItemById(Long id) {
        // 未起售的套餐不展示
        Setmeal setmeal = setmealMapper.getSetmealById(id);
        // 创建DishItemVO对象
        List<DishItemVO> dishItemVOList = new ArrayList<>();
        if (setmeal.getStatus() == StatusConstant.ENABLE) {
            List<SetmealDish> setmealDishList = setmealDishMapper.getSetmealDishBySetmealId(id);
            for (SetmealDish setmealDish : setmealDishList) {
                Long dishId = setmealDish.getDishId();
                Dish dish = dishMapper.getDishById(dishId);
                DishItemVO dishItemVO = DishItemVO.builder()
                        .name(dish.getName())
                        .image(dish.getImage())
                        .description(dish.getDescription())
                        .copies(setmealDish.getCopies())
                        .build();
                dishItemVOList.add(dishItemVO);
            }
        }
        return dishItemVOList;
    }
}
