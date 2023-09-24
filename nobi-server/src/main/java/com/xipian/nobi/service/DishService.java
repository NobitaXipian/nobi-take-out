package com.xipian.nobi.service;

import com.xipian.nobi.dto.DishDTO;
import com.xipian.nobi.dto.DishPageQueryDTO;
import com.xipian.nobi.result.PageResult;
import com.xipian.nobi.vo.DishVO;

import java.util.List;

/**
 * @author xipian
 * @date 2023/9/23
 */
public interface DishService {

    /**
     * 新增菜品和对应的口味
     *
     * @param dishDTO
     */
    void saveWithFlavor(DishDTO dishDTO);

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 菜品批量删除
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据id查询菜品和对应的口味数据
     *
     * @param id
     * @return
     */
    DishVO getByIdWithFlavor(Long id);

    /**
     * 根据id修改菜品基本信息和对应的口味信息
     *
     * @param dishDTO
     */
    void updateWithFlavor(DishDTO dishDTO);


    /**
     * 菜品起售停售
     *
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);
}
