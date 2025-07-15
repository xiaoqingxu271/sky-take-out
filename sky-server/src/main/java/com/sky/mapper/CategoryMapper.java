package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
* @author chun0
* @since 2025/7/6 14:34
* @version 1.0
*/
@Mapper
public interface CategoryMapper {

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    Page<Category> pageQueryCategory(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 新增分类
     * @param category
     */
    @Insert("insert into category (type, name, sort, status, create_time, update_time, create_user, update_user) values (#{type}, #{name}, #{sort}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void saveCategory(Category category);

    /**
     * 启用禁用分类
     * @param category
     */
    @Update("update category set status = #{status}, update_time = #{updateTime}, update_user = #{updateUser} where id = #{id}")
    @AutoFill(value = OperationType.UPDATE)
    void startOrStopCategory(Category category);

    /**
     * 根据分类类型查询分类信息
     * @param type
     * @return
     */
    @Select("select * from category where type = #{type}")
    List<Category> getCategoryInfoByType(Integer type);

    /**
     * 修改分类信息
     * @param category
     */
    @Update("update category set name = #{name}, sort = #{sort}, update_time = #{updateTime}, update_user = #{updateUser} where id = #{id}")
    @AutoFill(value = OperationType.UPDATE)
    void updateCategory(Category category);

    /**
     * 删除分类信息
     * @param id
     */
    @Delete("delete from category where id = #{id}")
    void deleteCategoryById(Long id);

    /**
     * 查询分类信息
     * @param type
     * @return
     */
    List<Category> list(Integer type);
}
