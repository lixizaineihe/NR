package com.itheima.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 用于封装相应信息的实体类
@NoArgsConstructor // lombok 的注解，用于提供无参构造函数
@AllArgsConstructor // lombok 的注解，用于提供全参构造函数
@Data // 这个注解一定要写！因为 SpringBoot 将 Result 对象序列化为 JSON 的时候需要 getter，不写这个注解会报错（或者自己生成 getter/setter 方法）
public class Result<T> {
    private Integer code; //业务状态码  0-成功  1-失败
    private String message; //提示信息
    private T data; //响应数据，不确定是什么类型，所以 Result 是泛型类

    // 返回的响应信息就分三种：操作成功（带响应数据）、操作成功（不带响应数据）、操作失败

    /**
     * 返回操作成功的响应信息（带响应数据）
     * 调用这个方法返回响应数据的时候就这么用：
     *      Result<String> rs = Result.success("Hello Spring!"); // data 是 String
     *      这样返回的 json 就是
     *      {
     *          "code": 0,
     *          "message": "操作成功",
     *          "data": "Hello Spring!"
     *      }
     */
    public static <E> Result<E> success(E data) {

        // 这里的全参构造函数由 @AllArgsConstructor 注解提供
        return new Result<>(0, "操作成功", data);
    }

    /**
     * 返回操作成功的响应信息（不带响应数据）
     * 调用这个方法返回响应数据的时候就这么用：
     *      Result rs = Result.success("Hello Spring!");
     *      这样返回的 json 就是
     *      {
     *          "code": 0,
     *          "message": "操作成功",
     *          "data": null
     *      }
     */
    public static Result success() {
        return new Result(0, "操作成功", null);
    }

    // 返回操作失败的响应信息
    public static Result error(String message) {
        return new Result(1, message, null);
    }
}
