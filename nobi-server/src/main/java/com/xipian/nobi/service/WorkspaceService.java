package com.xipian.nobi.service;

import com.xipian.nobi.vo.BusinessDataVO;
import com.xipian.nobi.vo.DishOverViewVO;
import com.xipian.nobi.vo.OrderOverViewVO;
import com.xipian.nobi.vo.SetmealOverViewVO;

import java.time.LocalDateTime;

/**
 * @author xipian
 * @date 2023/9/30
 */
public interface WorkspaceService {
    /**
     * 根据时间段统计营业数据
     * @param begin
     * @param end
     * @return
     */
    BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end);

    /**
     * 查询订单管理数据
     * @return
     */
    OrderOverViewVO getOrderOverView();

    /**
     * 查询菜品总览
     * @return
     */
    DishOverViewVO getDishOverView();

    /**
     * 查询套餐总览
     * @return
     */
    SetmealOverViewVO getSetmealOverView();

}
