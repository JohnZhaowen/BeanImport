package com.td.importBean.importBeanDefinitionRegistrar;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * author: zhaowen.he
 * date: 2020/1/21
 * ticket:
 * description:
 */
public class ImportBeanDefinitionRegistrarTest implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanDefinition beanDefinition =  new
                RootBeanDefinition("com.td.importBean.importBeanDefinitionRegistrar.BeanDefinitionRegistrarTest") ;
        registry.registerBeanDefinition("registrar", beanDefinition );
    }
}
