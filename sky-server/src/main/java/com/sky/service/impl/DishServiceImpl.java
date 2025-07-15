package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author chun0
* @since 2025/7/7 13:35
* @version 1.0
*/
@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQueryDish(DishPageQueryDTO dishPageQueryDTO) {
        // 开启分页
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        // 调用mapper分页查询
        Page<DishVO> page = dishMapper.pageQueryDish(dishPageQueryDTO);
        // 封装PageResult对象并返回
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 新增菜品
     * @param dishDTO
     */
    @Override
    public void saveDish(DishDTO dishDTO) {
        // 封装一个Dish对象
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        // 设置状态
        dish.setStatus(StatusConstant.ENABLE);
        // 调用Mapper向Dish表插入数据
        dishMapper.saveDish(dish);

        // 获取口味数据
        Long dishId = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        // 为每个口味数据设置菜品id
        if (flavors != null && flavors.size() > 0) {
            for (DishFlavor flavor : flavors) {
                flavor.setDishId(dishId);
            }
            dishFlavorMapper.saveDishFlavors(flavors);
        }
    }

    /**
     * 启用禁用菜品
     * @param status
     * @param id
     */
    @Override
    public void startOrStopDish(Integer status, Long id) {
        Dish dish = Dish.builder()
                .id(id)
                .status(status)
                .build();
        dishMapper.startOrStopDish(dish);
    }

    /**
     * 根据id查询菜品和对应的口味数据
     * @param id
     * @return
     */
    @Override
    public DishVO getDishById(Long id) {
        // 调用mapper查询菜品数据
        Dish dish = dishMapper.getDishById(id);
        // 调用mapper查询口味数据
        List<DishFlavor> dishFlavors = dishFlavorMapper.getDishFlavorsByDishId(id);
        // 封装DishVO对象并返回
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    @Override
    public List<Dish> getDishByCategoryId(Long categoryId) {
        return dishMapper.getDishByCategoryId(categoryId);
    }

    /**
     * 修改菜品
     * @param dishDTO
     */
    @Override
    public void updateDish(DishDTO dishDTO) {
        // 封装一个Dish对象
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        // 调用Mapper修改Dish表数据
        dishMapper.updateDish(dish);
        // 获取口味数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            // 删除该菜品对应的口味数据
            dishFlavorMapper.deleteDishFlavorsByDishId(dishDTO.getId());
            for (DishFlavor flavor : flavors) {
                flavor.setDishId(dishDTO.getId());
            }
            dishFlavorMapper.saveDishFlavors(flavors);
        }
    }

    /**
     * 批量删除菜品
     * @param ids
     */
    @Override
    public void deleteDishByIds(String ids) {
        String[] idArray = ids.split(",");
        for (String s : idArray) {
            Long id = Long.valueOf(s);
            Dish dish = dishMapper.getDishById(id);
            // 判断当前菜品是否在售
            if(dish.getStatus() == 1) {
                // 如果在起售中，则不能删除
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
            // 判断当前菜品是否关联了套餐
            List<SetmealDish> setmealDishList = setmealDishMapper.getSetmealDishByDishId(id);
            if(setmealDishList != null && setmealDishList.size() > 0) {
                // 如果已经关联了套餐，则不能删除
                throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
            }
            // 删除菜品数据
            dishMapper.deleteDishById(id);
            // 删除菜品对应的口味数据
            dishFlavorMapper.deleteDishFlavorsByDishId(id);
        }
    }

    /**
     * 条件查询
     * @param categoryId
     * @return
     */
    @Override
    public List<DishVO> list(Long categoryId) {
        // 查询dish表中起售的商品
        Dish dish = Dish.builder()
                .status(StatusConstant.ENABLE)
                .categoryId(categoryId)
                .build();
        List<Dish> dishList = dishMapper.listDish(dish);
        List<DishVO> dishVOList = new ArrayList<>();
        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            // 查询flavor表
            List<DishFlavor> dishFlavors = dishFlavorMapper.getDishFlavorsByDishId(d.getId());
            // 封装DishVO对象
            BeanUtils.copyProperties(d, dishVO);
            dishVO.setFlavors(dishFlavors);
            // 添加到集合中
            dishVOList.add(dishVO);
        }
        return dishVOList;
    }
}
