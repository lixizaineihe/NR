package com.itheima.mapper;

import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    /**
     * MyBatis 会自动把查询结果封装到 User 对象上，但前提是，user 表的字段名和 User 类成员变量的名字是一一对应的
     * 如果表中有 user_pic 这种带下划线的字段名，其对应的成员变量名应按照驼峰规则命名，即 userPic，但是这个要在配置文件里配置！在第 4 节课中会讲
     */
    @Select("select * from user where username=#{username}")
    User findUserByName(String username);

    @Insert("insert into user(username,password,create_time,update_time) values(#{username},#{md5password},now(),now())")
    void addUser(String username,String md5password);

    @Update("update user set nickname=#{nickname},email=#{email},update_time=#{updateTime} where id=#{id}")
    void update(User user);

    // 使用 now 方法获取现在的时间，注意和上面 SQL 语句的区别，上面是在业务层获取的当前时间
    @Update("update user set user_pic=#{avataeUrl}, update_time=now() where id=#{id}")
    void updateAvatar(String avatarUrl,Integer id);

    @Update("update user set password=#{md5String}, update_time=now() where id=#{id}")
    void update(String md5String, Integer id);
}
