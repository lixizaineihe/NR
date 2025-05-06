package com.itheima.anno;

import com.itheima.validation.StateValidation;
import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

// 被这个注解修饰的注解会被 javadoc 等文档工具提取到 API 文档中
@Documented

// Target 注解用于指定自定义注解 State 可以应用的位置，FIELD 指定 State 可以用于成员变量上，此外还有 TYPE（用于类）、METHOD（用于方法）等
@Target({FIELD})

// 指定自定义注解的生命周期。RUNTIME 表示该注解不仅会保留在源代码（.java 文件）和编译后的字节码（.class 文件）中，而且在运行时通过反射机制依然能够获取到这个注解信息
// 此外还有 SOURCE（注解只保留在源代码里，编译后就没了，无法通过反射获取）、CLASS（注解保留到字节码文件，但运行时 JVM 不会读取（默认值））
@Retention(RUNTIME)

// 加了 @Constraint 这个注解的注解，必定是用于校验的注解。在 StateValidation 这个类中定义了校验规则
@Constraint(validatedBy = {StateValidation.class})
public @interface State {

    // 提供校验失败后的提示信息
    String message() default "state 参数的值只能是“已发布”或“草稿”";

    // 有了这行代码，自定义的用于校验的 State 注解才能实现分组校验
    Class<?>[] groups() default {};

    // 负载。用于获取到 State 注解的附加信息（一般用不着，不管）
    Class<? extends jakarta.validation.Payload>[] payload() default {};

}
