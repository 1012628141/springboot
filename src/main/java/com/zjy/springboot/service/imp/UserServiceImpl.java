package com.zjy.springboot.service.imp;

import com.zjy.springboot.dao.UserInfoMapper;
import com.zjy.springboot.model.pojo.UserInfo;
import com.zjy.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service //springboot里面的事物，貌似不需要配置，只要直接加注解就可以了，亲测
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Transactional
    @Override
    public int addUser(UserInfo userInfo) {
        int addFlag = userInfoMapper.insert(userInfo);
        UserService j=null;
        j.selectAll(); //测试事物的回滚
        return addFlag;
    }

    @Override
    public List<UserInfo> selectAll() {
        List<UserInfo> userInfoList = userInfoMapper.selectByExample(null);
        return userInfoList;
    }
}
