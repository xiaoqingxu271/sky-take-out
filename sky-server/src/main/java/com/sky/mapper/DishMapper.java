package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author chun0
* @since 2025/7/7 13:37
* @version 1.0
*/
@Mapper
public interface DishMapper {
    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */

    Page<DishVO> pageQueryDish(DishPageQueryDTO dishPageQueryDTO);


    /**
     * 新增菜品
     * @param dish
     */
    @AutoFill(value = OperationType.INSERT)
    void saveDish(Dish dish);

    /**
     * 启用禁用菜品
     * @param dish
     */
    @AutoFill(value = OperationType.UPDATE)
    @Update("update dish set status = #{status}, update_time = #{updateTime}, update_user = #{updateUser} where id = #{id}")
    void startOrStopDish(Dish dish);

    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    @Select("select * from dish where id = #{id}")
    Dish getDishById(Long id);

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    @Select("select * from dish where category_id = #{categoryId}")
    List<Dish> getDishByCategoryId(Long categoryId);

    /**
     * 修改菜品
     * @param dish
     */
    @AutoFill(value = OperationType.UPDATE)
    void updateDish(Dish dish);

    /**
     * 批量删除菜品
     * @param id
     */
    @Delete("delete from dish where id in (#{id})")
    void deleteDishById(Long id);

    /**
     * 用户端查询分类下起售的商品
     * @param dish
     * @return
     */
    @Select("select * from dish where status = #{status} and category_id = #{categoryId}")
    List<Dish> listDish(Dish dish);

    /**
     * 查询指定状态的菜品数量
     * @param status
     * @return
     */
    @Select("select count(id) from dish where status = #{status}")
    Integer countByStatus(Integer status);

}

