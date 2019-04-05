package com.dubbo.myclass_service.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.dubbo.myclass_service.mapper.MyClassMapper;
import entity.MyClass;
import org.springframework.beans.factory.annotation.Autowired;
import service.IMyClassService;

import java.util.List;

@Service
public class MyClassServiceImpl implements IMyClassService {
    @Autowired
    private MyClassMapper myClassMapper;

    @Override
    public List<MyClass> getList() {
        return myClassMapper.selectList(null);
    }
}
