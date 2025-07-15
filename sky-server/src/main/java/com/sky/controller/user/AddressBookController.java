package com.sky.controller.user;

import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @author chun0
* @since 2025/7/11 15:26
* @version 1.0
*/
@RestController("userAddressBookController")
@RequestMapping("/user/addressBook")
@Api("c端地址薄相关接口")
@Slf4j
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @GetMapping("/list")
    @ApiOperation("查询当前用户所有地址")
    public Result<List<AddressBook>> list() {
        log.info("查询当前用户所有地址");
        List<AddressBook> list = addressBookService.list();
        return Result.success(list);
    }

    /**
     * 新增地址
     * @return
     */
    @PostMapping
    @ApiOperation("新增地址")
    public Result<String> addAddress(@RequestBody AddressBook addressBook) {
        log.info("新增地址：{}", addressBook);
        addressBookService.add(addressBook);
        return Result.success();
    }

    /**
     * 设置默认地址
     * @param addressBook
     * @return
     */
    @PutMapping("/default")
    @ApiOperation("设置默认地址")
    public Result<String> setDefaultAddress(@RequestBody AddressBook addressBook) {
        log.info("设置默认地址号：{}", addressBook.getId());
        addressBookService.setDefaultAddress(addressBook);
        return Result.success();
    }

    /**
     * 根据id查询地址
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询地址")
    public Result<AddressBook> getAddressBookById(@PathVariable Long id) {
        log.info("查询默认地址");
        AddressBook addressBook = addressBookService.getAddressBookById(id);
        return Result.success(addressBook);
    }

    /**
     * 修改地址
     * @param addressBook
     * @return
     */
    @PutMapping
    @ApiOperation("修改地址")
    public Result<String> updateAddress(@RequestBody AddressBook addressBook) {
        log.info("修改地址：{}", addressBook);
        addressBookService.update(addressBook);
        return Result.success();
    }

    /**
     * 删除地址
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation("删除地址")
    public Result<String> deleteAddress(Long id) {
        log.info("删除地址：{}", id);
        addressBookService.deleteAddress(id);
        return Result.success();
    }

    /**
     * 查询默认地址
     * @return
     */
    @GetMapping("/default")
    @ApiOperation("查询默认地址")
    public Result<AddressBook> getDefaultAddress() {
        log.info("查询默认地址");
        AddressBook addressBook = addressBookService.getDefaultAddress();
        return Result.success(addressBook);
    }

}
