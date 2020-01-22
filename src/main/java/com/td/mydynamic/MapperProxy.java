package com.td.mydynamic;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * author: zhaowen.he
 * date: 2020/1/22
 * ticket:
 * description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class MapperProxy<T> implements InvocationHandler {

    private Class<T> clazz;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }

        log.info("===代理类入参===[{}]", args);
        String result = JSON.toJSONString(args);
        log.info("===代理类执行结果===[{}]", result);
        return result;
    }
}
