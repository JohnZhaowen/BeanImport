/*
package com.td.td.scannerImport;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.env.Environment;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

*/
/**
 * author: zhaowen.he
 * date: 2020/1/21
 * ticket:
 * description:
 *//*

@Slf4j
public class MyClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    private static final String TEST_PROFILE = "test";

    private Class<? extends Annotation> annotationClass;

    private Class<?> markerInterface;

    */
/**
     * 排除自身项目包.
     *//*

    private String excludePackage;

    private RemoteServiceProxyFactoryBean<Object> exportServiceProxyFactoryBean = new RemoteServiceProxyFactoryBean<Object>();

    public MyClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, String excludePackage, Environment environment) {
        super(registry, true, environment);
        this.excludePackage = excludePackage;
    }

    public MyClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    public MyClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters, Environment environment) {
        super(registry, useDefaultFilters, environment);
    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    public void setMarkerInterface(Class<?> markerInterface) {
        this.markerInterface = markerInterface;
    }

    */
/**
     * Configures parent scanner to search for the right interfaces. It can search
     * for all interfaces or just for those that extends a markerInterface or/and
     * those annotated with the annotationClass
     *//*

    public void registerFilters() {
        boolean acceptAllInterfaces = true;

        // if specified, use the given annotation and / or marker interface
        if (this.annotationClass != null) {
            addIncludeFilter(new AnnotationTypeFilter(this.annotationClass));
            acceptAllInterfaces = false;
        }

        // override AssignableTypeFilter to ignore matches on the actual marker interface
        if (this.markerInterface != null) {
            addIncludeFilter(new AssignableTypeFilter(this.markerInterface) {
                @Override
                protected boolean matchClassName(String className) {
                    return false;
                }
            });
            acceptAllInterfaces = false;
        }

        if (acceptAllInterfaces) {
            // default include filter that accepts all classes
            addIncludeFilter(new TypeFilter() {
                @Override
                public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                    return true;
                }
            });
        }

        // exclude package-info.java
        addExcludeFilter(new TypeFilter() {
            @Override
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                String className = metadataReader.getClassMetadata().getClassName();
                if (excludePackage != null && className.startsWith(excludePackage)) {
                    return true;
                }

                return className.endsWith("package-info");
            }
        });
    }

    private void registerServiceMetadata(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            RemoteMethod annotation = method.getAnnotation(RemoteMethod.class);
            if (annotation != null) {
                String serviceName = annotation.serviceName();
                //String serviceDesc = annotation.serviceDesc();
                String methodName = method.getName();
                RemoteType remoteType = annotation.remoteType();
                String fullMethodName = clazz.getName() + "." + methodName;

                if (!serviceName.isEmpty()) {
                    ServiceMetadata metadata = new ServiceMetadata();
                    metadata.setServiceName(serviceName);
                    metadata.setServiceMethod(method);
                    metadata.setRemoteType(remoteType);
                    metadata.setInterfaceName(fullMethodName);
                    metadata.setServiceInterface(clazz);
                    ServiceManager.getInstance().put(fullMethodName, metadata);
                }
            }
        }
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

        if (beanDefinitions.isEmpty()) {
            log.warn("No ExportService interface was found in '" + Arrays.toString(basePackages) + "' package. Please check your configuration.");
        } else {
            processBeanDefinitions(beanDefinitions);
        }

        return beanDefinitions;
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        GenericBeanDefinition definition;
        for (BeanDefinitionHolder holder : beanDefinitions) {
            definition = (GenericBeanDefinition) holder.getBeanDefinition();

            log.info("Creating ExportServiceProxyFactoryBean with name '" + holder.getBeanName()
                    + "' and '" + definition.getBeanClassName() + "' mapperInterface");

            try {
                Class<?> clazz = definition.resolveBeanClass(Thread.currentThread().getContextClassLoader());
                RemoteService annotation = clazz.getAnnotation(RemoteService.class);
                if (annotation != null) {
                    // the export service interface is the original class of the bean
                    // but, the actual class of the bean is ExportServiceProxyFactoryBean
                    definition.getConstructorArgumentValues().addGenericArgumentValue(definition.getBeanClassName());
                    definition.setBeanClass(this.exportServiceProxyFactoryBean.getClass());
                    definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
                    definition.getPropertyValues().add(TEST_PROFILE, getEnvironment().acceptsProfiles(TEST_PROFILE));
                    registerServiceMetadata(clazz);
                }
            } catch (ClassNotFoundException ignore) {
                //ignore
                log.error("", ignore);
            }
        }
    }


    @Override
    protected boolean checkCandidate(String beanName, BeanDefinition beanDefinition) throws IllegalStateException {
        if (super.checkCandidate(beanName, beanDefinition)) {
            return true;
        } else {
            log.warn("Skipping MapperFactoryBean with name '" + beanName
                    + "' and '" + beanDefinition.getBeanClassName() + "' mapperInterface"
                    + ". Bean already defined with the same name!");
            return false;
        }
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }
}
*/
