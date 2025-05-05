package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.JwtUtil;
import com.itheima.utils.Md5Util;
import com.itheima.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated // 用于参数校验的注解
public class UserController {

    @Autowired
    private UserService userService;

    // 只接受 post 请求，浏览器地址栏和超链接发出的是 get 请求，表单可以通过 method 设置请求方式
    @PostMapping("/register") // @Pattern 是用于参数校验的注解，其参数为正则表达式，含义为 3-16 位非空字符
    // 这个方法，执行成功后响应的 JSON 的 data 部分为空，所以不用为泛型类 Result 指定特定的类型
    public Result Register(@Pattern(regexp = "^\\S{3,16}$")String username, @Pattern(regexp = "^\\S{3,16}$")String password) {
        User user=userService.findUserByName(username);
        if (user!=null) {
            return Result.error("用户名已存在");
        }else{
            userService.register(username,password);
            return Result.success();
        }
    }

    @PostMapping("/login")
    // 这个方法，执行成功后响应的 JSON 的 data 部分为 jwt 令牌，是字符串，所以泛型类 Result 的类型为 String
    public Result<String> login(@Pattern(regexp = "^\\S{3,16}$")String username, @Pattern(regexp = "^\\S{3,16}$")String password) {
        User user=userService.findUserByName(username);
        if (user==null) {
            return Result.error("用户名错误");
        }

        if(Md5Util.getMD5String(password).equals(user.getPassword())) {
            // 生成 jwt 令牌
            Map<String,Object> claims=new HashMap<>();
            claims.put("id",user.getId());
            claims.put("username",user.getUsername());
            String token= JwtUtil.genToken(claims);

            // 将令牌添加到响应信息中
            return Result.success(token);
        }

        return Result.error("密码错误");
    }

    @RequestMapping("/userInfo")
    // 这个方法，执行成功后响应的 JSON 的 data 部分为 User 对象，所以泛型类 Result 的类型为 User
    public Result<User> userInfo(@RequestHeader(name="Authorization") String token){

        // 从线程上下文中取解析 jwt 令牌的结果，获取用户名
        Map<String,Object> map= ThreadLocalUtil.get();
        String username=(String)map.get("username");

        User user=userService.findUserByName(username);
        return Result.success(user);
    }

    @PutMapping("/update")
    /**
     * 在接口文档中，规定了更新用户基本信息这部分的请求参数是 JSON
     * 所以这个 @RequestBody 注解的作用就是把 JSON 反序列化成 User 对象
     *
     * 这里还有一点要注解，就是控制器的最开始已经用了 @Validated 注解，那在这里还有必要加上 @Validated 注解吗？
     * 有必要！
     * 控制器上的 @Validated 注解只会激活方法参数上的校验，比如下面
     * 方法名(@Pattern(regexp = "^\\S{3,16}$")String username)
     * 但是实体类上的用于校验的注解，必须要在类对象作为方法参数时，在参数前面再加一次 @Validated 注解，就像这里
     */
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }

    /**
     * @RequestParam 注解用于获取 HTTP 请求中的参数，通常是 URL 中 ?key=value 形式的参数，或者表单提交的数据
     * 接口文档中说了，这个方法的请求参数是 Patch 请求，该请求 URL 一般长这样
     * PATCH /user/updateAvatar?avatarUrl=http://xxx.com/xxx.jpg
     * 这个注解的作用是获取这个 URL 中的 avatarUrl 键对应的参数值，然后赋值给 avatarUrl 这个参数
     *
     * 这个 @URL 是用于校验参数是否满足 URL 格式的注解，这个注解可以由控制器最上面的 @Validated 注解开启
     */
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    /**
     * 在接口文档中，规定了更新用户基本信息这部分的请求参数是 JSON
     * 所以这个 @RequestBody 注解的作用就是把 JSON 反序列化成 Map 对象
     */
    public Result updatePwd(@RequestBody Map<String,String> params){

        // 参数校验
        String oldPwd=params.get("old_pwd");
        String newPwd=params.get("new_pwd");
        String rePwd=params.get("re_pwd");

        if(!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)){
            return Result.error("缺少必要的参数");
        }

        // 比较用户输入的原密码和用户表中的原密码是否一致
        Map<String,Object> map=ThreadLocalUtil.get();
        String username=(String)map.get("username");
        User user=userService.findUserByName(username);
        if(!user.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码错误");
        }

        // 比较用户输入的 newPwd 和 rePwd 是否一致
        if(!rePwd.equals(newPwd)){
            return Result.error("两次填写的新密码不一致");
        }

        userService.updatePwd(newPwd);
        return Result.success();
    }
}
