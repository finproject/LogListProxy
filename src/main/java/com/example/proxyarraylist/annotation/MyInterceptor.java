package com.example.proxyarraylist.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

@Slf4j
@SuppressWarnings("rawtypes")
public class MyInterceptor<T> implements InvocationHandler {
    private final T t;
    private final Pair pair;

    public MyInterceptor(T t, Pair pair) {
        this.t = t;
        this.pair = pair;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object result = method.invoke(t, args);

        List list = null;
        if (t instanceof List) list = (List) t;

        ParameterizedType type = null;
        Type genericType = pair.getKey().getGenericType();
        if (genericType instanceof ParameterizedType) type = (ParameterizedType) genericType;

        String typeList = Objects.requireNonNull(type).toString();
        int listSize = Objects.requireNonNull(list).size();

        String nameMethod = method.getName();
        if (nameMethod.contains("add") || nameMethod.contains("remove") || nameMethod.contains("clear")) {
            log.info(String.format("Size of %s in Class %s = %d", typeList, pair.getValue(), listSize));
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getProxy(T t, Class<? super T> interfaceType, Pair pair) {
        MyInterceptor handler = new MyInterceptor(t, pair);
        return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(),
                new Class<?>[]{interfaceType}, handler
        );
    }
}
