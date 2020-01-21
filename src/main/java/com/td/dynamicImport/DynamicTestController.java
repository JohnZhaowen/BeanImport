package com.td.dynamicImport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: zhaowen.he
 * date: 2020/1/21
 * ticket:
 * description:
 */

@RestController
public class DynamicTestController {

    @Autowired
    private TestService testService;

    @Autowired
    private CalculateService calculateService;

    @GetMapping("/dynamicTest")
    public String getHello() {
        String testList = testService.getList("code123","name456");
        String calculateResult = calculateService.getResult("测试");
        return (testList + ", " +calculateResult);
    }

}
