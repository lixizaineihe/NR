package com.itheima.pojo;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDateTime;

@Data // lombok 提供的注解，用于在编译阶段，自动为实体类生成 getter、setter 和 toString 方法，就不用我们手写了
public class User {

    /**
     * 当控制器方法的参数被 @Request 修饰，表示其接收的参数是由前端的 JSON 反序列得到的类对象
     * 在反序列化的过程中，这个 @NotNull 注解规定了该类成员变量的数据不能为空
     */
    @NotNull
    private Integer id;//主键ID
    private String username;//用户名

    /**
     * 当把 User 对象序列化为 JSON 作为响应信息返回给前端时
     * @JsonIgnore 可以确保 password 不被序列化到 JSON 的键值对中
     */
    @JsonIgnore
    private String password;//密码

    // 在反序列化过程中，该成员变量的值不能为 null 或空字符串/空集合
    @NotEmpty
    @Pattern(regexp="^\\S{1,10}$") // 正则表达式校验，该成员变量的值必须是 1-10 位的非空字符
    private String nickname;//昵称

    @NotEmpty
    @Email // 该成员变量的值必须符合邮箱格式
    private String email;//邮箱
    private String userPic;//用户头像地址
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
