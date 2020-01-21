package com.td.beanFactory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * author: zhaowen.he
 * date: 2020/1/21
 * ticket:
 * description:
 */
@Slf4j
@Data
public class BeanFactoryServiceImpl implements BeanFactoryService {

    @Override
    public void run() {
        log.info("beanFactoryServiceImpl");
    }

    @Override
    public String generateBeanName() {
        return "beanFactoryServiceImpl";
    }
}
