package com.zjy.springboot.service.imp;

import com.zjy.springboot.dao.UserInfoMapper;
import com.zjy.springboot.model.pojo.UserInfo;
import com.zjy.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public int addUser(UserInfo userInfo) {
       return userInfoMapper.insert(userInfo);
    }

    @Override
    public List<UserInfo> selectAll() {
        List<UserInfo> userInfoList = userInfoMapper.selectByExample(null);
        return userInfoList;
    }
}
