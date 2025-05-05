package com.itheima.execption;

import com.itheima.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 这个注解是 @ControllerAdvice + @ResponseBody 的组合
 * 作用是全局拦截和处理所有 @RestController 或 @Controller 产生的异常，并把返回值作为 JSON 写入响应体
 */
@RestControllerAdvice
public class GlobalExecptionHandler {

    // 使用该注解的方法会拦截所有类型为 Exception 及其子类的异常
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace(); // 打印异常堆栈信息到控制台

        /**
         * StringUtils.hasLength 用于判断异常有没有信息
         * 有的话就把这个信息作为参数传到 error 方法中
         * 没有的话就把 “操作失败” 作为参数传到 error 方法中
         */
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败");
    }
}
