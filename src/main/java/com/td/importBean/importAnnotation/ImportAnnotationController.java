package com.td.importBean.importAnnotation;

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
public class ImportAnnotationController {

    @Autowired
    private ImportAnnotationService service;

    @GetMapping("/importAnnotation")
    public String run(){
        return service.run();
    }
}
