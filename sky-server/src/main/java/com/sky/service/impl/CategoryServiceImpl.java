package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author chun0
* @since 2025/7/6 14:09
* @version 1.0
*/
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQueryCategory(CategoryPageQueryDTO categoryPageQueryDTO) {
        // 开启分页
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());

        // 调用mapper分页查询
        Page<Category> page = categoryMapper.pageQueryCategory(categoryPageQueryDTO);

        // 将Page对象转换成PageResult对象并返回
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 新增分类
     * @param categoryDTO
     */
    @Override
    public void saveCategory(CategoryDTO categoryDTO) {
        // 封装分类的参数
        Category category = Category.builder()
                .id(categoryDTO.getId())
                .name(categoryDTO.getName())
                .type(categoryDTO.getType())
                .sort(categoryDTO.getSort())
                .status(StatusConstant.ENABLE)
                .build();
        // 调用mapper保存数据
        categoryMapper.saveCategory(category);
    }

    /**
     * 启用禁用分类
     * @param status
     * @param id
     */
    @Override
    public void startOrStopCategory(String status, Long id) {
        Category category = Category.builder()
                .status(Integer.valueOf(status))
                .id(id)
                .build();
        // 调用mapper启用禁用分类
        categoryMapper.startOrStopCategory(category);

    }

    /**
     * 根据分类类型查询
     * @param type
     * @return
     */
    @Override
    public List<Category> getCategoryInfoByType(Integer type) {
        // 调用Mapper层查询
        return categoryMapper.getCategoryInfoByType(type);
    }


    /**
     * 修改分类
     * @param categoryDTO
     */
    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        // 封装分类数据
        Category category = Category.builder()
                .id(categoryDTO.getId())
                .type(categoryDTO.getType())
                .name(categoryDTO.getName())
                .sort(categoryDTO.getSort())
                .build();
        // 调用Mapper层修改分类数据
        categoryMapper.updateCategory(category);
    }

    /**
     * 删除分类
     * @param id
     */
    @Override
    public void deleteCategoryById(Long id) {
        // 如果当前分类关联了菜品，就无法删除
        List<Dish> dishList = dishMapper.getDishByCategoryId(id);
        if (dishList != null && dishList.size() > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
        // 如果当前分类关联了套餐，就无法删除
        List<Setmeal> setmealList = setmealMapper.getSetmealByCategoryId(id);
        if (setmealList != null && setmealList.size() > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }
        categoryMapper.deleteCategoryById(id);
    }

    @Override
    public List<Category> list(Integer type) {
        return categoryMapper.list(type);
    }
}
