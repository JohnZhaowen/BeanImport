package com.td.importBean.importSelector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: zhaowen.he
 * date: 2020/1/21
 * ticket:
 * description:
 */
@RestController
public class ImportSelectorController {

    @Autowired
    private ImportSelectorService1 testService1;

    @Autowired
    private ImportSelectorService2 testService2;

    @RequestMapping("/importSelector")
    public String test(){
        return testService1.test() + " : " + testService2.test();
    }


}
