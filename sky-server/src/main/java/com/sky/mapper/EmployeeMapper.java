package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username username of employee we want to search
     * @return info of employee we find
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * insert employee
     * @param employee info of employee we want to add
     */
    @Insert("insert into sky_take_out.employee (employee.name, employee.username, employee.password, employee.phone, employee.sex, employee.id_number, employee.status, employee.create_time, employee.update_time, employee.create_user, employee.update_user) " +
            "values (#{name}, #{username}, #{password}, #{phone}, #{sex}, #{idNumber}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void insert(Employee employee);

    Page<Employee> getPageEmployee(EmployeePageQueryDTO employeePageQueryDTO);

    @AutoFill(value = OperationType.UPDATE)
    void update(Employee employee);

    @Select("select * from sky_take_out.employee where employee.id = #{id}")
    Employee getById(Long id);
}
