package com.xipian.nobi.controller.user;

import com.xipian.nobi.dto.OrdersSubmitDTO;
import com.xipian.nobi.result.Result;
import com.xipian.nobi.service.OrderService;
import com.xipian.nobi.vo.OrderSubmitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xipian
 * @date 2023/9/27
 */
@RestController("userOrderController")
@RequestMapping("/user/order")
@Api(tags = "用户端订单接口")
public class OrderController {

    @Resource
    private OrderService orderService;
    /**
     * 用户下单
     * @param ordersSubmitDTO
     * @return
     */
    @PostMapping("/submit")
    @ApiOperation("用户下单")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO){
        OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }
}
