package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO info of employee who log in
     * @return info of employee who log in and jwt
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * add employee
     * @param employeeDTO
     * @return
     */
    @PostMapping
    @ApiOperation("add employee")
    public Result<String> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        log.info("new employee: {}", employeeDTO);
        employeeService.add(employeeDTO);
        return Result.success();
    }

    /**
     * get employee for a page by name
     * @param employeePageQueryDTO info about name and page
     * @return a page list
     */
    @GetMapping("/page")
    @ApiOperation("get employees by page number")
    public Result<PageResult> getEmployeesByPage(EmployeePageQueryDTO employeePageQueryDTO) {
        PageResult pageResult = employeeService.employeesOnPage(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * change the status of the employee
     * @param status value of status need to change
     * @param id employee's id
     * @return success
     */
    @PostMapping("/status/{status}")
    @ApiOperation("changeStatus")
    public Result<String> enableOrDisableEmployee(@PathVariable Integer status, Long id) {
        employeeService.enableOrDisableEmp(status, id);
        return Result.success();
    }

    @PutMapping
    @ApiOperation("update employee")
    public Result<String> updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.updateEmployee(employeeDTO);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("get employee by Id")
    public Result<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return Result.success(employee);
    }
}
