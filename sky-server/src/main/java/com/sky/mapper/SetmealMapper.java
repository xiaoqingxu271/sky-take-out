package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

/**
* @author chun0
* @since 2025/7/8 14:15
* @version 1.0
*/
@Mapper
public interface SetmealMapper {

    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    Page<Setmeal> pageQuerySetmeal(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 新增套餐
     * @param setmeal
     */
    @AutoFill(value = OperationType.INSERT)
    void saveSetmeal(Setmeal setmeal);

    /**
     * 启售或停售套餐
     * @param setmeal
     */
    @Select("update setmeal set status = #{status},update_time = #{updateTime}, update_user = #{updateUser} where id = #{id}")
    @AutoFill(value = OperationType.UPDATE)
    void startOrStopSetmeal(Setmeal setmeal);

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @Select("select * from setmeal where id = #{id}")
    Setmeal getSetmealById(Long id);

    /**
     * 修改套餐
     * @param setmeal
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Setmeal setmeal);

    /**
     * 批量删除套餐
     * @param idList
     */
    void deleteSetmeals(List<Long> idList);

    /**
     * 根据分类id查询套餐
     * @param id
     * @return
     */
    @Select("select * from setmeal where category_id = #{id}")
    List<Setmeal> getSetmealByCategoryId(Long id);

    /**
     * 根据用户id查询购物车
     * @param userId
     * @return
     */
    @Select("select * from shopping_cart where user_id = #{userId}")
    List<ShoppingCart> getShoppingCartByUserId(Long userId);

    /**
     * 统计状态为1或者2的套餐数量
     * @param enable
     * @return
     */
    @Select("select count(id) from setmeal where status = #{status}")
    Integer countByStatus(Integer enable);
}
