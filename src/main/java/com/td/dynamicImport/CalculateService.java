package com.td.dynamicImport;

import com.td.dynamicImport.annotation.DynamicImport;

@DynamicImport
public interface CalculateService {

    String getResult(String name);
}
