package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO info of employee sent from web
     * @return info of employee who log in
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void add(EmployeeDTO employeeDTO);

    PageResult employeesOnPage(EmployeePageQueryDTO employeePageQueryDTO);

    void enableOrDisableEmp(Integer status, Long id);

    void updateEmployee(EmployeeDTO employeeDTO);

    Employee getEmployeeById(Long id);
}
