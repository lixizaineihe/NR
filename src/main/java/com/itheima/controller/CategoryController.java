package com.itheima.controller;

import com.itheima.pojo.Category;
import com.itheima.pojo.Result;
import com.itheima.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // @PostMapping 后没有加映射路径，这意味着只有请求路径为 /category 的 Post 请求由这个方法处理
    @PostMapping
    public Result addCategory(@RequestBody @Validated(Category.Add.class) Category category) {
        categoryService.add(category);
        return Result.success();
    }

    // @GetMapping 后没有加映射路径，这意味着只有请求路径为 /category 的 Get 请求由这个方法处理
    @GetMapping
    // 响应类 Result 的 data 成员变量的值是列表，列表中是 Category 对象，所以泛型类 Result 的类型是 List<Category>
    public Result<List<Category>> list(){
        List<Category> cs=categoryService.list();
        return Result.success(cs);
    }

    @GetMapping("/details")
    public Result<Category> details(Integer id){
        Category c=categoryService.findById(id);
        return Result.success(c);
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Category category){
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Integer id){
        categoryService.delete(id);
        return Result.success();
    }
}
