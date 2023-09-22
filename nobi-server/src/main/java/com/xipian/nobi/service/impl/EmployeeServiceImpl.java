package com.xipian.nobi.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xipian.nobi.constant.MessageConstant;
import com.xipian.nobi.constant.PasswordConstant;
import com.xipian.nobi.constant.StatusConstant;
import com.xipian.nobi.context.BaseContext;
import com.xipian.nobi.dto.EmployeeDTO;
import com.xipian.nobi.dto.EmployeeLoginDTO;
import com.xipian.nobi.dto.EmployeePageQueryDTO;
import com.xipian.nobi.entity.Employee;
import com.xipian.nobi.exception.AccountLockedException;
import com.xipian.nobi.exception.AccountNotFoundException;
import com.xipian.nobi.exception.PasswordErrorException;
import com.xipian.nobi.mapper.EmployeeMapper;
import com.xipian.nobi.result.PageResult;
import com.xipian.nobi.service.EmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    public static final String SALT = "xipian";

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //md5加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+password).getBytes());
        if (!encryptPassword.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * 新增员工
     *
     * @param employeeDTO
     */
    @Override
    public void save(EmployeeDTO employeeDTO) {
        //拷贝属性
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        //设置默认密码,md5加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+PasswordConstant.DEFAULT_PASSWORD).getBytes());
        employee.setPassword(encryptPassword);
        //设置默认状态
        employee.setStatus(StatusConstant.ENABLE);
        //设置创建人和修改人
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.insert(employee);

    }

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        PageHelper.startPage(employeePageQueryDTO.getPage(),employeePageQueryDTO.getPageSize());
        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 启用禁用员工账号
     * @param status
     * @param id
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        Employee employee = Employee.builder()
                .status(status)
                .id(id)
                .build();
        employeeMapper.update(employee);
    }

    /**
     * 根据id查找员工信息
     * @param id
     * @return
     */
    @Override
    public Employee getById(Long id) {
        Employee employee = employeeMapper.getById(id);
        employee.setPassword("******");
        return employee;
    }

    /**
     * 更新员工信息
     * @param employeeDTO
     */
    @Override
    public void update(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.update(employee);
    }

}
