package com.itheima.service.impl;

import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.Md5Util;
import com.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UseServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByName(String username) {
        User user=userMapper.findUserByName(username);
        return user;
    }

    @Override
    public void register(String username, String password) {
        //加密
        String md5password = Md5Util.getMD5String(password);
        userMapper.addUser(username,md5password);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        // 从线程上下文中获取用户 id
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id=(Integer)map.get("id");

        userMapper.updateAvatar(avatarUrl,id);
    }

    @Override
    public void updatePwd(String newPwd) {
        // 从线程上下文中获取用户 id
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id=(Integer)map.get("id");

        userMapper.update(Md5Util.getMD5String(newPwd),id);
    }
}
