package com.sky.service;

import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
* @author chun0
* @since 2025/7/14 16:21
* @version 1.0
*/    
public interface WorkSpaceService {

    /**
     * 查询今日运营数据
     * @return
     */
    BusinessDataVO getBusinessData(LocalDateTime beginTime, LocalDateTime endTime);

    /**
     * 查询订单统计数据
     * @return
     */
    OrderOverViewVO getOrderOverView();

    /**
     * 获取菜品总览
     * @return
     */
    DishOverViewVO getDishOverView();

    /**
     * 获取套餐总览
     * @return
     */
    SetmealOverViewVO getSetmealOverView();


}
