package com.td.mydynamic;

import com.td.mydynamic.annotation.MyMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import sun.awt.image.ImageWatched;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * author: zhaowen.he
 * date: 2020/1/22
 * ticket:
 * description:
 */
@Component
public class MapperDynamicBeanRegister implements ApplicationContextAware, BeanDefinitionRegistryPostProcessor, ResourceLoaderAware {

    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    private ApplicationContext applicationContext;

    private ResourcePatternResolver resourcePatternResolver;

    private MetadataReaderFactory metadataReaderFactory;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
/*        Set<Class<?>> clazzs = scannerPackages("com.td.mydynamic");
        for(Class clazz : clazzs){
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MapperFactoryBean.class);
            builder.addConstructorArgValue(clazz);

            GenericBeanDefinition bd = (GenericBeanDefinition)builder.getRawBeanDefinition();
            bd.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
            registry.registerBeanDefinition(clazz.getSimpleName(), bd);
        }*/

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Set<Class<?>> clazzs = scannerPackages("com.td.mydynamic");
        for(Class clazz : clazzs){
            MapperFactoryBean bean = new MapperFactoryBean(clazz);
            beanFactory.registerSingleton(clazz.getSimpleName(), bean);
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {

        this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        this.metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);
    }

    private Set<Class<?>> scannerPackages(String basePackage){

        Set<Class<?>> candidates = new LinkedHashSet<>();
        String path = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + resolveBasePackage(basePackage) + '/' + DEFAULT_RESOURCE_PATTERN;

        try {
            Resource[] resources = resourcePatternResolver.getResources(path);
            for(Resource resource : resources){
                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                String className = metadataReader.getClassMetadata().getClassName();
                Class<?> clazz = Class.forName(className);
                if(clazz.isAnnotationPresent(MyMapper.class)){
                    candidates.add(clazz);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return candidates;
    }


    protected String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(this.getEnvironment().resolveRequiredPlaceholders(basePackage));
    }

    private Environment getEnvironment() {
        return applicationContext.getEnvironment();
    }
}




