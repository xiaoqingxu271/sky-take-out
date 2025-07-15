package com.sky.controller.user;

import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* @author chun0
* @since 2025/7/9 19:43
* @version 1.0
*/
@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
@Api("用户端套餐接口")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * 查询套餐
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("查询套餐")
    @Cacheable(cacheNames = "setmeal", key = "#categoryId")
    public Result<List<Setmeal>> list(Long categoryId) {
        log.info("查询套餐：{}", categoryId);
        List<Setmeal> setmealList = setmealService.list(categoryId);
        return Result.success(setmealList);
    }

    /**
     * 根据套餐id查询菜品选项
     * @param id
     * @return
     */
    @GetMapping("/dish/{id}")
    @ApiOperation("根据套餐id查询的菜品")
    public Result<List<DishItemVO>> getDishItemById(@PathVariable Long id) {
        log.info("查询套餐下的菜品：{}", id);
        List<DishItemVO> dishItemVOList = setmealService.getDishItemById(id);
        return Result.success(dishItemVOList);
    }
}
