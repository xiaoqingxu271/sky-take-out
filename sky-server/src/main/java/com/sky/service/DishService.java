package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQueryDish(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 新增菜品和对应的口味
     * @param dishDTO
     */
    void saveDish(DishDTO dishDTO);

    /**
     * 菜品起售停售
     * @param status
     * @param id
     */
    void startOrStopDish(Integer status, Long id);

    /**
     * 根据id查询菜品和对应的口味数据
     * @param id
     * @return
     */
    DishVO getDishById(Long id);

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    List<Dish> getDishByCategoryId(Long categoryId);

    /**
     * 修改菜品
     * @param dishDTO
     */
    void updateDish(DishDTO dishDTO);

    /**
     * 批量删除菜品
     * @param ids
     */
    void deleteDishByIds(String ids);

    /**
     * 根据分类id查询菜品选项
     * @param categoryId
     * @return
     */
    List<DishVO> list(Long categoryId);
}
