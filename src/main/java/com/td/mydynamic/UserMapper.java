package com.td.mydynamic;

import com.td.mydynamic.annotation.MyMapper;

@MyMapper
public interface UserMapper {

    String getUser(Long id, String tenant);
}
