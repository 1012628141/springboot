package com.zjy.springboot.service;

import com.zjy.springboot.model.pojo.UserInfo;

import java.util.List;

public interface UserService {
    int addUser(UserInfo userInfo);

    List<UserInfo> selectAll();
}
