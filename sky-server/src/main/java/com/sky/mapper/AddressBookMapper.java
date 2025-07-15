package com.sky.mapper;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
* @author chun0
* @since 2025/7/11 15:33
* @version 1.0
*/
@Mapper
public interface AddressBookMapper {

    /**
     * 查询指定用户的所有地址
     * @return
     */
    @Select("select * from address_book where user_id = #{userId}")
    List<AddressBook> list(Long userId);

    /**
     * 新增地址
     * @param addressBook
     */
    @Insert("insert into address_book (user_id, consignee, phone, sex, province_code, province_name, city_code, city_name, district_code, district_name, detail, label, is_default) " +
            "values (#{userId}, #{consignee}, #{phone}, #{sex}, #{provinceCode}, #{provinceName}, #{cityCode}, #{cityName}, #{districtCode}, #{districtName}, #{detail}, #{label}, #{isDefault})")
    void add(AddressBook addressBook);

    /**
     * 设置默认地址
     * @param addressBook
     */
    @Update("update address_book set is_default = #{isDefault} where user_id = #{userId} and id = #{id}")
    void setDefaultAddress(AddressBook addressBook);

    /**
     * 根据id查询地址
     * @param id
     * @return
     */
    @Select("select * from address_book where id = #{id}")
    AddressBook getAddressBookById(Long id);

    /**
     * 修改地址
     * @param addressBook
     */
    @Update("update address_book set consignee = #{consignee}, phone = #{phone}, sex = #{sex}, province_code = #{provinceCode}, province_name = #{provinceName}, city_code = #{cityCode}, city_name = #{cityName}, district_code = #{districtCode}, district_name = #{districtName}, detail = #{detail}, label = #{label}" +
            " where user_id = #{userId} and id = #{id}")
    void update(AddressBook addressBook);

    /**
     * 删除地址
     * @param id
     */
    @Delete("delete from address_book where id = #{id}")
    void deleteAddress(Long id);

    /**
     * 查询默认地址
     * @param userId
     * @return
     */
    @Select("select * from address_book where user_id = #{userId} and is_default = 1")
    AddressBook getDefaultAddress(Long userId);
}
