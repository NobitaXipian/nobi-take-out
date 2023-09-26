package com.xipian.nobi.service.impl;

import com.xipian.nobi.context.BaseContext;
import com.xipian.nobi.dto.ShoppingCartDTO;
import com.xipian.nobi.entity.Dish;
import com.xipian.nobi.entity.Setmeal;
import com.xipian.nobi.entity.ShoppingCart;
import com.xipian.nobi.mapper.DishMapper;
import com.xipian.nobi.mapper.SetmealMapper;
import com.xipian.nobi.mapper.ShoppingCartMapper;
import com.xipian.nobi.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xipian
 * @date 2023/9/27
 */
@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Resource
    private ShoppingCartMapper shoppingCartMapper;
    @Resource
    private DishMapper dishMapper;
    @Resource
    private SetmealMapper setmealMapper;

    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        //判断当前加入的商品是否已经存在
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        //已存在则执行update，num++
        if (list != null && list.size() >0){
            ShoppingCart shoppingCartResult = list.get(0);
            shoppingCartResult.setNumber(shoppingCartResult.getNumber()+1);
            shoppingCartMapper.updateNumById(shoppingCartResult);
            return;
        }

        //不存在则插入数据库
        if (shoppingCartDTO.getDishId() != null){
            //本次添加到购物车的是菜品
            Dish dish = dishMapper.getById(shoppingCartDTO.getDishId());
            shoppingCart.setName(dish.getName());
            shoppingCart.setImage(dish.getImage());
            shoppingCart.setAmount(dish.getPrice());
        }else {
            //本次添加到购物车的是套餐
            Setmeal setmeal = setmealMapper.getById(shoppingCartDTO.getSetmealId());
            shoppingCart.setName(setmeal.getName());
            shoppingCart.setImage(setmeal.getImage());
            shoppingCart.setAmount(setmeal.getPrice());
        }

        shoppingCart.setNumber(1);
        shoppingCart.setCreateTime(LocalDateTime.now());
        shoppingCartMapper.insert(shoppingCart);

    }

    /**
     * 查看购物车
     * @return
     */
    @Override
    public List<ShoppingCart> showShoppingCart() {
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(userId)
                .build();
        return shoppingCartMapper.list(shoppingCart);
    }

    /**
     * 清空购物车
     */
    @Override
    public void cleanShoppingCart() {
        Long userId = BaseContext.getCurrentId();
        shoppingCartMapper.deleteByUserId(userId);
    }


    /**
     * 删除购物车中一个商品
     * @param shoppingCartDTO
     */
    @Override
    public void subShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        if (list != null && list.size() > 0){
            shoppingCart = list.get(0);
            if (shoppingCart.getNumber() != 1){
                shoppingCart.setNumber(shoppingCart.getNumber() - 1);
                shoppingCartMapper.updateNumById(shoppingCart);
            }else {
                shoppingCartMapper.deleteById(shoppingCart.getId());
            }
        }
    }
}
