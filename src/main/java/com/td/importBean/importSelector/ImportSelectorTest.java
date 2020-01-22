package com.td.importBean.importSelector;

import com.td.importBean.annotation.SelectorBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * author: zhaowen.he
 * date: 2020/1/21
 * ticket:
 * description:
 */
public class ImportSelectorTest implements ImportSelector, ResourceLoaderAware {
    /*@Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        //返回值就是 要加入IOC容器的Bean的全类名
        return new String[]{"com.td.importBean.importSelector.ImportSelectorService1","com.td.importBean.importSelector.ImportSelectorService2"};

    }*/

    private static final String DEFAULT_RESOURCE_PATTERN = "/**/*.class";

    private ResourcePatternResolver resourcePatternResolver;

    private MetadataReaderFactory metadataReaderFactory;

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return scannerPackages("com.td.importBean.importSelector");
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        this.metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);
    }

    private String[] scannerPackages(String basePackage){
        List<String> set = new ArrayList<>();
        String path = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                basePackage.replace(".", "/") +
                DEFAULT_RESOURCE_PATTERN;

        try {
            Resource[] resources = resourcePatternResolver.getResources(path);
            for(Resource resource : resources){
                if(resource.isReadable()){
                    MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                    String className = metadataReader.getClassMetadata().getClassName();
                    Class<?> clazz = Class.forName(className);
                    if(clazz.isAnnotationPresent(SelectorBean.class)){
                        set.add(className);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] result = new String[set.size()];
        set.toArray(result);

        return result;
    }
}
