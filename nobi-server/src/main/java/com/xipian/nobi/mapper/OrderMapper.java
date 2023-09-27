package com.xipian.nobi.mapper;

import com.xipian.nobi.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xipian
 * @date 2023/9/27
 */
@Mapper
public interface OrderMapper {

    /**
     * 插入订单数据
     * @param orders
     */
    void insert(Orders orders);
}
