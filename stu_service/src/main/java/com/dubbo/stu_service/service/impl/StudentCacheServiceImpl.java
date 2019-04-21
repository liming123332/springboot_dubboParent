package com.dubbo.stu_service.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.dubbo.stu_service.mapper.StudentMapper;
import entity.MyClass;
import entity.Student;
import entity.StudentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import service.IMyClassService;
import service.IStudentService;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentCacheServiceImpl implements IStudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Reference
    private IMyClassService myClassService;

    @Override
    public List<Student> getList() {
        return studentMapper.selectList(null);
    }

    @Override
    @Cacheable(cacheNames = "stu",key = "'stuList'")
    public List<StudentVO> getStuAndClassList() {
        List<StudentVO> studentVOs=new ArrayList<>();
        List<Student> studentList = studentMapper.selectList(null);
        List<MyClass> myClassList = myClassService.getList();
        for (Student student : studentList) {
            for (MyClass myClass : myClassList) {
                if(student.getCid()==myClass.getId()){
                    StudentVO studentVO=new StudentVO();
                    BeanUtils.copyProperties(student,studentVO);
                    studentVO.setMyClass(myClass);
                    studentVOs.add(studentVO);
                }
            }
        }
        return studentVOs;
    }

    @Override
    @CacheEvict(cacheNames = "stu",key = "'stuList'")
    public void add(Student student) {
        studentMapper.insert(student);
    }

    @Override
    @Cacheable(cacheNames = "stu",key = "'stu'+#id")
    public StudentVO selectById(Integer id) {
        Student student = studentMapper.selectById(id);
        MyClass myClass = myClassService.selectById(student.getCid());
        StudentVO studentVO=new StudentVO();
        BeanUtils.copyProperties(student, studentVO);
        studentVO.setMyClass(myClass);
        //System.out.println(studentVO.getId());
        return studentVO;
    }

    @Override
    @CacheEvict(cacheNames = "stu",key = "'stuList'")
    public void updateById(Student student) {
        studentMapper.updateById(student);
    }

    @Override
    @CacheEvict(cacheNames = "stu",key = "'stuList'")
    public void delete(Integer id) {
        studentMapper.deleteById(id);
    }
}
