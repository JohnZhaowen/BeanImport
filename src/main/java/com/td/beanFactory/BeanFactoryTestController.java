package com.td.beanFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: zhaowen.he
 * date: 2020/1/21
 * ticket:
 * description:
 */
@RestController
public class BeanFactoryTestController {

    @Autowired
    private RegisterBeanHandler registerBeanHandler;

    @GetMapping("/beanFactory")
    public String getBeanFromBeanFactory(){

        BeanFactoryServiceImpl service = new BeanFactoryServiceImpl();
        registerBeanHandler.register(service);
        registerBeanHandler.invoke(service);

        return "succes";
    }
}
