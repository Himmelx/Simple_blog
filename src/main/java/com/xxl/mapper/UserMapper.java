package com.xxl.mapper;

import com.xxl.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xxl
 * @create 2021-10-25 22:05
 */

@Mapper
@Repository        // 由spring接管，写component注解，但是这里dao层，写这个注解更标准
public interface UserMapper {
    //获取所有用户
    List<User> getUserList();

    //用户分页后获取数据
    List<User> getUserListByPage(int page,int limit);

    //获取用户数量
    int getUserCount();

    //根据id查找用户
    User queryUserById(@Param("userId") int id);
    //根据用户名查找用户
    User queryUserByName(@Param("userName") String name);
    //添加用户,只需要用户名和密码即可
    int addUser(User user);
    //修改用户
    int updateUser(User user);
    //删除用户
    int deleteUser(@Param("userId")int id);

    //根据用户名模糊查询
    List<User> queryUserLikeName(@Param("userName") String name);

}
