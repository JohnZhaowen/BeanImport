package com.td.importBean.importSelector;

import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * author: zhaowen.he
 * date: 2020/1/21
 * ticket:
 * description:
 */
@Component
@Import(ImportSelectorTest.class)
public class ImportSelectorConfig {
}
