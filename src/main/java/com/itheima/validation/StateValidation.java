package com.itheima.validation;

import com.itheima.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State, String> {

    /**
     * StateValidation 作为自定义的用于校验的注解的实现类，它必须实现 ConstraintValidator<A,T> 接口
     * A 是要处理的注解，T 是被校验的数据的类型
     * 并且必须要实现下面的两个方法
     */

    // 该方法会在约束注解实例化时被调用，可以用来做初始化操作
    // 我们这里没有什么特定的操作，下面这是自动生成的代码
    @Override
    public void initialize(State constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    // 该方法定义实际的校验逻辑
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        // s 是要校验的数据
        if(s==null){
            return false; // 返回 false 代表校验不通过
        }
        if(s.equals("已发布") || s.equals("草稿")){
            return true; // 返回 true 代表校验通过
        }
        return false;
    }
}
