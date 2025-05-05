package com.itheima.controller;

import com.itheima.pojo.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @GetMapping("/list")
    // 把 Authorization 这个请求头的值赋给 token
    public Result<String> list(@RequestHeader(name="Authorization") String token, HttpServletResponse response){
        return Result.success("所有文章的数据...");
    }
}
