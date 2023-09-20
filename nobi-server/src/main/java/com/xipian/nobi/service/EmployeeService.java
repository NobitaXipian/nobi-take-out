package com.xipian.nobi.service;

import com.xipian.nobi.dto.EmployeeLoginDTO;
import com.xipian.nobi.entity.Employee;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

}
