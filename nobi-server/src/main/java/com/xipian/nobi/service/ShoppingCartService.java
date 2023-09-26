package com.xipian.nobi.service;

import com.xipian.nobi.dto.ShoppingCartDTO;
import com.xipian.nobi.entity.ShoppingCart;

import java.util.List;

/**
 * @author xipian
 * @date 2023/9/27
 */
public interface ShoppingCartService {

    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * 查看购物车
     * @return
     */
    List<ShoppingCart> showShoppingCart();

    /**
     * 清空购物车
     */
    void cleanShoppingCart();

    /**
     * 删除购物车中一个商品
     * @param shoppingCartDTO
     */
    void subShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
