package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author chun0
* @since 2025/7/8 14:45
* @version 1.0
*/
@Mapper
public interface SetmealDishMapper {

    /**
     * 批量插入套餐和菜品的关联关系
     * @param setmealDishes
     */
    void saveSetmealDish(List<SetmealDish> setmealDishes);

    /**
     * 根据套餐id查询菜品id的集合
     * @param id
     * @return
     */
    @Select("select dish_id from setmeal_dish where setmeal_id = #{id}")
    List<Long> getDishIdsBySetmealId(Long id);

    /**
     * 根据套餐id查询套餐和菜品的关联关系
     * @param id
     * @return
     */
    @Select("select * from setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> getSetmealDishBySetmealId(Long id);

    /**
     * 根据套餐id删除套餐和菜品的关联关系
     * @param setmealId
     */
    @Delete("delete from setmeal_dish where setmeal_id = #{setmealId}")
    void deleteBySetmealId(Long setmealId);

    /**
     * 根据id批量删除套餐和菜品的关联关系
     * @param idList
     */
    void deleteBySetmealIds(List<Long> idList);

    /**
     * 根据菜品id查询套餐和菜品的关联关系
     * @param id
     * @return
     */
    @Select("select * from setmeal_dish where dish_id = #{id}")
    List<SetmealDish> getSetmealDishByDishId(Long id);
}
