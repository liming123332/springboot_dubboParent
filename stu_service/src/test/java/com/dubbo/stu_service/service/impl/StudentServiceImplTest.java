package com.dubbo.stu_service.service.impl;

import com.dubbo.stu_service.mapper.StudentMapper;
import entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import service.IStudentService;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceImplTest {
    @Autowired(required = false)
    private StudentMapper studentMapper;

    @Test
    public void test(){
        List<Student> list = studentMapper.selectList(null);
        for (Student student : list) {
            System.out.println(student);
        }
    }

}