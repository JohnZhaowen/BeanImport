package com.td.dynamicImport;

import com.td.dynamicImport.annotation.DynamicImport;

@DynamicImport
public interface TestService {

    String getList(String code, String name);

}
