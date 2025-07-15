package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.exception.AddressBookBusinessException;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author chun0
* @since 2025/7/11 15:31
* @version 1.0
*/
@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    /**
     * 查询地址列表
     * @return
     */
    @Override
    public List<AddressBook> list() {
        Long userId = BaseContext.getCurrentId();
        return addressBookMapper.list(userId);
    }

    /**
     * 新增地址
     * @param addressBook
     */
    @Override
    public void add(AddressBook addressBook) {
        Long userId = BaseContext.getCurrentId();
        addressBook.setUserId(userId);
        // 不设置默认地址
        addressBook.setIsDefault(0);
        addressBookMapper.add(addressBook);
    }

    /**
     * 设置默认地址
     * @param addressBook
     */
    @Override
    public void setDefaultAddress(AddressBook addressBook) {
        Long userId = BaseContext.getCurrentId();
        addressBook.setUserId(userId);
        // 清空默认地址
        List<AddressBook> list = addressBookMapper.list(userId);
        for (AddressBook ab : list) {
            if (ab.getIsDefault() == 1) {
                ab.setIsDefault(0);
                addressBookMapper.setDefaultAddress(ab);
            }
        }
        // 设置默认地址
        addressBook.setIsDefault(1);
        addressBookMapper.setDefaultAddress(addressBook);
    }

    /**
     * 根据id查询地址
     * @param id
     * @return
     */
    @Override
    public AddressBook getAddressBookById(Long id) {
        return addressBookMapper.getAddressBookById(id);
    }

    /**
     * 修改地址
     * @param addressBook
     */
    @Override
    public void update(AddressBook addressBook) {
        Long userId = BaseContext.getCurrentId();
        addressBook.setUserId(userId);
        addressBookMapper.update(addressBook);
    }

    /**
     * 删除地址
     * @param id
     */
    @Override
    public void deleteAddress(Long id) {
        addressBookMapper.deleteAddress(id);
    }

    @Override
    public AddressBook getDefaultAddress() {
        Long userId = BaseContext.getCurrentId();
        return addressBookMapper.getDefaultAddress(userId);

    }
}
