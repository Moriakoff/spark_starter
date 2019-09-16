package com.naya.spring_starter.annotations;

import com.naya.spring_starter.annotations.AutowiredBroadcast;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

public class AutowiredBroadcastBeanPostProcessor implements BeanPostProcessor {

    private final JavaSparkContext sc;

    private final ApplicationContext applicationContext;

    public AutowiredBroadcastBeanPostProcessor(JavaSparkContext sc, ApplicationContext applicationContext) {
        this.sc = sc;
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(AutowiredBroadcast.class)) {
                ParameterizedType genericType = (ParameterizedType) declaredField.getGenericType();
                Class<?> configClass = (Class<?>) genericType.getActualTypeArguments()[0];
                declaredField.setAccessible(true);
                Object configurationBean = applicationContext.getBean(configClass);
                Broadcast<Object> broadcast = sc.broadcast(configurationBean);
                ReflectionUtils.setField(declaredField, bean, broadcast);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }
}
