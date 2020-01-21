package com.td.importBean.importBeanDefinitionRegistrar;

import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * author: zhaowen.he
 * date: 2020/1/21
 * ticket:
 * description:
 */
@Component
@Import(ImportBeanDefinitionRegistrarTest.class)
public class BeanDefinitionRegistrarConfig {
}
