package com.xxl.service;

import com.xxl.pojo.Admin;
import com.xxl.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xxl
 * @create 2021-11-27 8:49
 */
@Service
public interface AdminService {
    //获取所有管理员
    List<Admin> getAdminList();
    //根据id查找管理员
    Admin queryAdminById(@Param("adminId") int id);
    //根据用户名查找管理员
    Admin queryAdminByName(@Param("adminName") String name);
    //添加管理员,只需要用户名和密码即可
    int addAdmin(Admin admin);
    //修改管理员
    int updateAdmin(Admin admin);
    //删除管理员
    int deleteAdmin(@Param("adminId")int id);
}
