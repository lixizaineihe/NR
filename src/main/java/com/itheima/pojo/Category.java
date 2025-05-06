package com.itheima.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Category {

    @NotNull(groups = Update.class)
    private Integer id;//主键ID

    @NotEmpty // 默认分组中的校验项
    private String categoryName;//分类名称

    @NotEmpty // 默认分组中的校验项
    private String categoryAlias;//分类别名
    private Integer createUser;//创建人ID

    // 指定实体类序列化为 JSON 时的格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间

    /**
     * 未指定分组的校验项属于默认分组
     * A extends B 后，A 分组得到 B 分组中全部的校验项
     * 我们没有为 categoryName 和 categoryAlias 指定分组，所以这两个校验项属于默认分组
     * Add 和 Update 继承了默认分组后，就拥有了默认分组中的校验项
     *
     */
    public interface Add extends Default {

    }

    public interface Update extends Default{

    }
}
