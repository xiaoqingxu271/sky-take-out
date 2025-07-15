package com.sky.controller.admin;

import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.EscapedErrors;
import org.springframework.web.bind.annotation.*;

/**
* @author chun0
* @since 2025/7/8 13:49
* @version 1.0
*/
@Slf4j
@RestController
@RequestMapping("/admin/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * 分页查询套餐
     * @param setmealPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分页查询套餐")
    public Result<PageResult> pageQuerySetmeal(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("分页查询套餐 {}", setmealPageQueryDTO);
        PageResult pageResult = setmealService.pageQuerySetmeal(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 新增套餐
     * @param setmealDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增套餐")
    @CacheEvict(cacheNames = "setmeal", key = "#setmealDTO.categoryId")
    public Result<String> saveSetmeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐 {}", setmealDTO);
        setmealService.saveSetmeal(setmealDTO);
        return Result.success();
    }

    /**
     * 起售或停售套餐
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("起售或停售套餐")
    @CacheEvict(cacheNames = "setmeal", allEntries = true)
    public Result<String> startOrStopSetmeal(@PathVariable Integer status, Long id) {
        log.info("起售或停售套餐 {}", id);
        setmealService.startOrStopSetmeal(status, id);
        return Result.success();
    }

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐")
    public Result<SetmealVO> getSetmealById(@PathVariable Long id) {
        log.info("查询套餐 {}", id);
        SetmealVO setmealVO = setmealService.getSetmealById(id);
        return Result.success(setmealVO);
    }

    /**
     * 修改套餐
     * @param setmealDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改套餐")
    @CacheEvict(cacheNames = "setmeal", allEntries = true)
    public Result<String> updateSetmealAndSetmealDish(@RequestBody SetmealDTO setmealDTO) {
        log.info("修改套餐 {}", setmealDTO);
        setmealService.update(setmealDTO);
        return Result.success();
    }

    /**
     * 批量删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("批量删除套餐")
    @CacheEvict(cacheNames = "setmeal", allEntries = true)
    public Result<String> deleteSetmeals(String ids) {
        log.info("批量删除套餐 {}", ids);
        setmealService.deleteSetmeals(ids);
        return Result.success();
    }
}
