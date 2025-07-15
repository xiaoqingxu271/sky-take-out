package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
* @author chun0
* @since 2025/7/7 15:08
* @version 1.0
*/
@Mapper
public interface DishFlavorMapper {
    /**
     * 批量保存菜品口味数据
     * @param flavors
     * @return
     */
    void saveDishFlavors(List<DishFlavor> flavors);

    /**
     * 根据菜品id查询对应的口味数据
     * @param id
     * @return
     */
    @Select("select * from dish_flavor where dish_id = #{id}")
    List<DishFlavor> getDishFlavorsByDishId(Long id);

    /**
     * 根据菜品id删除菜品口味数据
     * @param id
     */
    @Delete("delete from dish_flavor where dish_id = #{id}")
    void deleteDishFlavorsByDishId(Long id);
}
