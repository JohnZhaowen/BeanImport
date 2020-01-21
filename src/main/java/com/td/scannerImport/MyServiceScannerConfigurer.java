/*
package com.td.td.scannerImport;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

*/
/**
 * author: zhaowen.he
 * date: 2020/1/21
 * ticket:
 * description:
 *//*

@Component
@Slf4j
public class MyServiceScannerConfigurer implements BeanDefinitionRegistryPostProcessor,
        InitializingBean, ApplicationContextAware {

    */
/** 要扫描的包 *//*

    private String basePackage;

    */
/** 排除自身项目包 *//*

    private String excludePackage;

    */
/** applicationContext上下文 通过实现ApplicationContextAware 获取 *//*

    private ApplicationContext applicationContext;

    */
/** beanName生成器 *//*

    private BeanNameGenerator nameGenerator;

    */
/** set方法指定要被扫描的包名 *//*

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public BeanNameGenerator getNameGenerator() {
        return nameGenerator;
    }

    public void setNameGenerator(BeanNameGenerator nameGenerator) {
        this.nameGenerator = nameGenerator;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.basePackage, "Property 'basePackage' is required");
    }


    */
/** 实现BeanDefinitionRegistryPostProcessor接口 此方法中实例化 ClassPathRemoteServiceScanner 扫描器  *//*

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        log.info("BeanFactoryPostProcessor postProcessBeanDefinitionRegistry start...");
        //实例化扫描器
        MyClassPathBeanDefinitionScanner scanner = new MyClassPathBeanDefinitionScanner(registry, excludePackage, applicationContext.getEnvironment());
        //设置要扫描的注解
        scanner.setAnnotationClass(RemoteService.class);
        //传入applicationContext
        scanner.setResourceLoader(this.applicationContext);
        //传入beaanName生成器
        scanner.setBeanNameGenerator(this.nameGenerator);
        //过滤 不需要扫描的类
        scanner.registerFilters();

        scanner.scan(StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // left intentionally blank
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setExcludePackage(String excludePackage) {
        this.excludePackage = excludePackage;
    }


}
*/
