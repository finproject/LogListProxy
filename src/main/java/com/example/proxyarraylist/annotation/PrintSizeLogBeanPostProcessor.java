package com.example.proxyarraylist.annotation;

import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

@Component
public class PrintSizeLogBeanPostProcessor implements BeanPostProcessor {

    @Override
    @SneakyThrows
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(PrintSizeLog.class)) {
                field.setAccessible(true);
                Object o = field.get(bean);
                List list = null;
                if (o instanceof List) list = (List) o;
                field.set(bean, MyInterceptor.getProxy(list, List.class, new Pair(field, beanName)));
            }
        }
        return bean;
    }
}
