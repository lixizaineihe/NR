package com.itheima.mapper;

import com.itheima.pojo.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ArticleMapper {

    @Insert("insert into article(title,content,cover_img,state,category_id,create_user,create_time,update_time)"+
            "values(#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime})")
    void add(Article article);

    @Update("update article set title = #{title}, content = #{content}, cover_img = #{coverImg}, state = #{state}"+
            "category_id = #{categoryId}, create_user = #{createUser}, update_time = #{updateTime}"+
            "WHERE id = #{id}")
    void update(Article article);
}
