package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
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
     * 启用禁用id
     * @param status
     * @param id
     */
    void startOrStop(String status, Long id);

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    Employee getEmployeeInfoById(Long id);

    /**
     * 修改员工信息
     * @param employeeDTO
     */
    void updateEmployeeInfo(EmployeeDTO employeeDTO);

    /**
     * 修改员工密码
     * @param passwordEditDTO
     */
    void editPassword(PasswordEditDTO passwordEditDTO);
}
