package com.sky.service.impl;

import com.sky.constant.StatusConstant;
import com.sky.entity.Orders;
import com.sky.mapper.DishMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.WorkSpaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
* @author chun0
* @since 2025/7/14 16:22
* @version 1.0
*/
@Service
public class WorkSpaceServiceImpl implements WorkSpaceService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public BusinessDataVO getBusinessData(LocalDateTime beginTime, LocalDateTime endTime) {
        Map map = new HashMap();
        map.put("begin", beginTime);
        map.put("end", endTime);
        // 计算订单总数
        Integer totalOrderCount = orderMapper.countByMap(map);
        // 计算新增用户数
        Integer newUsers = userMapper.countByMap(map);
        map.put("status", Orders.COMPLETED);
        // 计算营业额
        Double turnover = orderMapper.sumByMap(map);
        turnover = turnover == null ? 0.0 : turnover;
        // 计算有效订单数
        Integer validOrderCount = orderMapper.countByMap(map);
        // 计算订单完成率和平均客单价
        Double orderCompletionRate = 0.0;
        Double unitPrice = 0.0;
        if (totalOrderCount != 0) {
            orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
        }
        if (validOrderCount != 0) {
            DecimalFormat df = new DecimalFormat("0.00");
            unitPrice = Double.valueOf(df.format(turnover / validOrderCount));
        }

        return BusinessDataVO.builder()
                .turnover(turnover)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .unitPrice(unitPrice)
                .newUsers(newUsers)
                .build();

    }

    /**
     * 获取订单 OverView
     * @return
     */
    @Override
    public OrderOverViewVO getOrderOverView() {
        Integer waitingOrders = orderMapper.countStatus(Orders.PENDING_PAYMENT);
        Integer deliveredOrders = orderMapper.countStatus(Orders.DELIVERY_IN_PROGRESS);
        Integer completedOrders = orderMapper.countStatus(Orders.COMPLETED);
        Integer cancelledOrders = orderMapper.countStatus(Orders.CANCELLED);
        Integer allOrders = orderMapper.countStatus(null);
        return OrderOverViewVO.builder()
                .waitingOrders(waitingOrders)
                .deliveredOrders(deliveredOrders)
                .completedOrders(completedOrders)
                .cancelledOrders(cancelledOrders)
                .allOrders(allOrders)
                .build();
    }

    /**
     * 获取菜品总览
     * @return
     */
    @Override
    public DishOverViewVO getDishOverView() {
        Integer sold = dishMapper.countByStatus(StatusConstant.ENABLE);
        Integer discontinued = dishMapper.countByStatus(StatusConstant.DISABLE);
        return DishOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }

    /**
     * 获取套餐总览
     * @return
     */
    @Override
    public SetmealOverViewVO getSetmealOverView() {
        Integer sold = setmealMapper.countByStatus(StatusConstant.ENABLE);
        Integer discontinued = setmealMapper.countByStatus(StatusConstant.DISABLE);
        return SetmealOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }
}
