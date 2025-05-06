package com.itheima.service;

import com.itheima.pojo.Category;

import java.util.List;

public interface CategoryService {

    // 新增文章分类
    void add(Category category);

    // 文章分类列表查询
    List<Category> list();

    // 根据 id 查询分类信息
    Category findById(Integer id);

    // 更新分类
    void update(Category category);

    // 删除分类
    void delete(Integer id);
}
