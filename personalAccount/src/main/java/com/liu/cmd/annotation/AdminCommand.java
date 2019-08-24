package com.liu.cmd.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/*
 *  @Retention(RetentionPolicy.RUNTIME)
 *      注解不仅被保存在class文件中，JVM加载class文件后仍然存在
 *  @Target(ElementType.TYPE)
 *      说明Annotation所修饰的对象范围：
 *      用于描述类、接口（包括注解类型）或enum声明
 *
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AdminCommand {
}
