package com.xipian.nobi.service;

import com.xipian.nobi.dto.OrdersSubmitDTO;
import com.xipian.nobi.vo.OrderSubmitVO;

/**
 * @author xipian
 * @date 2023/9/27
 */
public interface OrderService {

    /**
     * 用户下单
     * @param ordersSubmitDTO
     * @return
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);
}
