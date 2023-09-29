package com.xipian.nobi.task;

import com.xipian.nobi.entity.Orders;
import com.xipian.nobi.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单定时任务类
 * @author xipian
 * @date 2023/9/29
 */
@Component
@Slf4j
public class OrderTask {

    @Resource
    private OrderMapper orderMapper;

    /**
     * 处理超时订单，每分钟触发一次
     */
    @Scheduled(cron = "0 * * * * ? ")
    public void handleTimeoutOrder(){
        log.info("定时处理超时订单：{}", LocalDateTime.now());
        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);
        List<Orders> ordersList = orderMapper.getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT, time);
        if(ordersList != null && ordersList.size() > 0){
            Orders ordersNew = new Orders();
            ordersNew.setStatus(Orders.CANCELLED);
            ordersNew.setCancelReason("订单超时，自动取消");
            ordersNew.setCancelTime(LocalDateTime.now());
            for (Orders orders : ordersList) {
                ordersNew.setId(orders.getId());
                orderMapper.update(ordersNew);
            }
        }
    }

    /**
     * 处理一直处于派送中状态的订单
     */
    @Scheduled(cron = "0 0 1 * * ?") //每天凌晨1点触发一次
    public void processDeliveryOrder(){
        log.info("定时处理处于派送中的订单：{}",LocalDateTime.now());
        LocalDateTime time = LocalDateTime.now().plusMinutes(-60);
        List<Orders> ordersList = orderMapper.getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, time);
        if(ordersList != null && ordersList.size() > 0){
            Orders ordersNew = new Orders();
            ordersNew.setStatus(Orders.COMPLETED);
            for (Orders orders : ordersList) {
                ordersNew.setId(orders.getId());
                orderMapper.update(orders);
            }
        }
    }
}
