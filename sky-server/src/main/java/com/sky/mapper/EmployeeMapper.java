package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);


    /**
     * 插入员工数据
     * @param employee
     */
    @Insert("insert into employee values (#{id},#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void save(Employee employee);

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 启用禁用员工账号
     * @param employee
     *
     */
    @Update("update employee set status = #{status}, update_time=#{updateTime}, update_user=#{updateUser} where id = #{id}")
    void startOrStop(Employee employee);

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @Select("select * from employee where id = #{id}")
    Employee getEmployeeInfoById(Long id);

    /**
     * 编辑员工信息
     * @param employee
     */
    @Update("update employee set username = #{username}, name = #{name}, phone = #{phone}, sex = #{sex}, id_number = #{idNumber}, update_time = #{updateTime}, update_user = #{updateUser} where id = #{id}")
    void updateEmployeeInfo(Employee employee);

    /**
     * 修改密码
     * @param password
     * @param updateTime
     */
    @Update("update employee set password = #{password}, update_time = #{updateTime} where id = #{empId}")
    void editPassword(String password, LocalDateTime updateTime, Long empId);
}
