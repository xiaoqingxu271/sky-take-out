package com.sky.service.impl;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

/**
* @author chun0
* @since 2025/7/10 12:01
* @version 1.0
*/
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;
    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        // 判断当前菜品或套餐是否在购物车中
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        if (list != null && list.size() > 0) {
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartMapper.updateShoppingCartById(cart);
        } else {
            // 如果不在购物车中，则添加到购物车中
            Long dishId = shoppingCart.getDishId();
            // 判断当前添加的是菜品还是套餐
            if (dishId != null) {
                // 添加的是菜品
                Dish dish = dishMapper.getDishById(dishId);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
                shoppingCart.setCreateTime(LocalDateTime.now());
            } else {
                // 添加的是套餐
                Long setmealId = shoppingCart.getSetmealId();
                Setmeal setmeal = setmealMapper.getSetmealById(setmealId);
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);
        }


    }

    /**
     * 查看购物车
     * @return
     */
    @Override
    public List<ShoppingCart> list() {
        Long userId = BaseContext.getCurrentId();
        return setmealMapper.getShoppingCartByUserId(userId);
    }

    /**
     * 删除购物车中的一个商品
     * @param shoppingCartDTO
     */
    @Override
    public void sub(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(BaseContext.getCurrentId());
        Long dishId = shoppingCartDTO.getDishId();
        List<ShoppingCart> list = null;
        if (dishId != null) {
            // 删除的就是菜品
            shoppingCart.setDishId(dishId);
            shoppingCart.setDishFlavor(shoppingCartDTO.getDishFlavor());
            list = shoppingCartMapper.list(shoppingCart);
            shoppingCart = list.get(0);
            if (shoppingCart.getNumber() == 1) {
                shoppingCartMapper.deleteShoppingCartOne(shoppingCart);
            } else {
                // 更新数据
                shoppingCart.setNumber(shoppingCart.getNumber() - 1);
                shoppingCartMapper.updateShoppingCartNumber(shoppingCart);
            }
        } else {
            // 删除的就是套餐
            Long setmealId = shoppingCartDTO.getSetmealId();
            shoppingCart.setSetmealId(setmealId);
            list = shoppingCartMapper.list(shoppingCart);
            shoppingCart = list.get(0);
            if (shoppingCart.getNumber() == 1) {
                shoppingCartMapper.deleteShoppingCartOne(shoppingCart);
            } else {
                shoppingCart.setNumber(shoppingCart.getNumber() - 1);
                shoppingCartMapper.updateShoppingCartNumber(shoppingCart);
            }
        }
    }

    /**
     * 清空购物车
     */
    @Override
    public void clean() {
        Long userId = BaseContext.getCurrentId();
        shoppingCartMapper.clean(userId);
    }
}
