package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

/**
* @author chun0
* @since 2025/7/11 15:30
* @version 1.0
*/

public interface AddressBookService {
    /**
     * 查询所有地址薄中的地址
     * @param
     */
    List<AddressBook> list();

    /**
     * 新增地址
     * @param addressBook
     */
    void add(AddressBook addressBook);

    /**
     * 设置默认地址
     * @param addressBook
     */
    void setDefaultAddress(AddressBook addressBook);

    /**
     * 根据id查询地址
     * @param id
     */
    AddressBook getAddressBookById(Long id);

    /**
     * 修改地址
     * @param addressBook
     */
    void update(AddressBook addressBook);

    /**
     * 删除地址
     * @param id
     */
    void deleteAddress(Long id);

    /**
     * 查询默认地址
     * @return
     */
    AddressBook getDefaultAddress();


}
