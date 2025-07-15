package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.WorkSpaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
* @author chun0
* @since 2025/7/14 16:19
* @version 1.0
*/
@RestController
@RequestMapping("/admin/workspace")
@Api("工作台接口")
@Slf4j
public class WorkSpaceController {

    @Autowired
    private WorkSpaceService workSpaceService;

    /**
     * 查询今日运营数据
     * @return
     */
    @GetMapping("/businessData")
    @ApiOperation("查询今日运营数据")
    public Result<BusinessDataVO> getBusinessData() {
        log.info("获取工作台数据");
        LocalDate now = LocalDate.now();
        LocalDateTime beginTime = LocalDateTime.of(now, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(now, LocalTime.MAX);
        BusinessDataVO businessDataVO = workSpaceService.getBusinessData(beginTime, endTime);
        return Result.success(businessDataVO);
    }

    /**
     * 获取订单统计数据
     * @return
     */
    @GetMapping("/overviewOrders")
    @ApiOperation("获取订单统计数据")
    public Result<OrderOverViewVO> getOrderOverView() {
        log.info("获取订单统计数据");
        OrderOverViewVO orderOverViewVO = workSpaceService.getOrderOverView();
        return Result.success(orderOverViewVO);
    }

    /**
     * 获取菜品总览
     * @return
     */
    @GetMapping("/overviewDishes")
    @ApiOperation("获取菜品总览")
    public Result<DishOverViewVO> getDishOverView() {
        log.info("获取菜品总览");
        DishOverViewVO dishOverViewVO = workSpaceService.getDishOverView();
        return Result.success(dishOverViewVO);
    }

    /**
     * 获取套餐总览
     * @return
     */
    @GetMapping("/overviewSetmeals")
    @ApiOperation("获取套餐总览")
    public Result<SetmealOverViewVO> getSetmealOverView() {
        log.info("获取套餐总览");
        SetmealOverViewVO setmealOverViewVO = workSpaceService.getSetmealOverView();
        return Result.success(setmealOverViewVO);
    }
}
