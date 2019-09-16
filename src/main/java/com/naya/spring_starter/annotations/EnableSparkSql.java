package com.naya.spring_starter.annotations;

import com.naya.spring_starter.SparkConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(SparkConfiguration.class)
public @interface EnableSparkSql {
}
