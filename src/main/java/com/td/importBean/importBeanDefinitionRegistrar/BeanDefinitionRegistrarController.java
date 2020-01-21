package com.td.importBean.importBeanDefinitionRegistrar;

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
public class BeanDefinitionRegistrarController {

    @Autowired
    private BeanDefinitionRegistrarTest beanDefinitionRegistrarTest;

    @GetMapping("/BeanDefinitionRegistrar")
    public String run(){
        return beanDefinitionRegistrarTest.test();
    }

}
