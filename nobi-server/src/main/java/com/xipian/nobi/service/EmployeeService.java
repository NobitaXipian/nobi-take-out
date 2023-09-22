package com.xipian.nobi.service;

import com.xipian.nobi.dto.EmployeeDTO;
import com.xipian.nobi.dto.EmployeeLoginDTO;
import com.xipian.nobi.dto.EmployeePageQueryDTO;
import com.xipian.nobi.entity.Employee;
import com.xipian.nobi.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 员工新增
     * @param employeeDTO
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 启用禁用员工账号
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 根据id查找员工信息
     * @param id
     * @return
     */
    Employee getById(Long id);

    /**
     * 更新员工信息
     * @param employeeDTO
     */
    void update(EmployeeDTO employeeDTO);
}
