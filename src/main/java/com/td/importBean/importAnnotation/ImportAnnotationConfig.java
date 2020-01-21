package com.td.importBean.importAnnotation;

import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * author: zhaowen.he
 * date: 2020/1/21
 * ticket:
 * description:
 */
@Import(ImportAnnotationService.class)
@Component
public class ImportAnnotationConfig {
}
