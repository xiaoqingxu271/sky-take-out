package com.sky.controller.user;

import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* @author chun0
* @since 2025/7/9 14:05
* @version 1.0
*/
@RestController("userDishController")
@RequestMapping("/user/dish")
@Api("用户端菜品接口")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询菜品列表
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("查询菜品列表")
    public Result<List<DishVO>> list(Long categoryId) {
        log.info("查询菜品：{}", categoryId);
        // 先构造要查询的key
        String key = "dish_" + categoryId;
        // 从Redis中查询
        List<DishVO> dishVOList = (List<DishVO>) redisTemplate.opsForValue().get(key);
        // 判断缓存中是否有数据
        if (dishVOList != null && dishVOList.size() > 0) {
            // 缓存中有数据，直接返回
            return Result.success(dishVOList);
        }
        // 缓存中没有数据，查询数据库
        dishVOList = dishService.list(categoryId);
        // 将数据保存到Redis中
        redisTemplate.opsForValue().set(key, dishVOList);
        return Result.success(dishVOList);
    }
}
