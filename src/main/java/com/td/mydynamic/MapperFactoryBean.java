package com.td.mydynamic;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * author: zhaowen.he
 * date: 2020/1/22
 * ticket:
 * description:
 */
@AllArgsConstructor
public class MapperFactoryBean<T> implements FactoryBean<T> {

    private Class<T> clazz;

    @Override
    public T getObject() {
        MapperProxy<T> proxy = new MapperProxy<>(clazz);
        return (T)Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, proxy);
    }

    @Override
    public Class<?> getObjectType() {
        return clazz;
    }
}
