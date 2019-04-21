package com.dubbo.stu_service.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.dubbo.stu_service.mapper.StudentMapper;
import com.dubbo.stu_service.utils.RedisLockUtil;
import entity.MyClass;
import entity.Student;
import entity.StudentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import service.IMyClassService;
import service.IStudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class StudentServiceImpl implements IStudentService{
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    //redis模版对象
    private RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    private RedisLockUtil redisLockUtil;

    @Reference
    private IMyClassService myClassService;

    @Override
    @Cacheable(cacheNames = "students")
    public List<Student> getList() {
        return studentMapper.selectList(null);
    }

    @Override
    public List<StudentVO> getStuAndClassList() {
        List<StudentVO> studentVOs=new ArrayList<>();
        //redis字符串的序列化器
        RedisSerializer redisSerializer=new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);

        //查缓存
        List<Student> studentList= (List<Student>) redisTemplate.opsForValue().get("studentList");
//        //双重检测锁
//        if(null==studentList) {
//            studentList= (List<Student>) redisTemplate.opsForValue().get("studentList");
//            synchronized (this) {
//                if (null == studentList) {
//                    studentList = studentMapper.selectList(null);
//                    redisTemplate.opsForValue().set("studentList", studentList);
//                }
//            }
//        }
        Boolean flag = redisLockUtil.lock("lock1", 120);
        if(null==studentList) {
            if (flag) {
                studentList = studentMapper.selectList(null);
                redisTemplate.opsForValue().set("studentList", studentList);
                redisTemplate.expire("studentList", 5, TimeUnit.MINUTES);
                redisLockUtil.unlock("lock1");
            } else {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return this.getStuAndClassList();
            }
        }
        List<MyClass> myClassList= (List<MyClass>) redisTemplate.opsForValue().get("myClassList");
//        if(null==myClassList) {
//            myClassList= (List<MyClass>) redisTemplate.opsForValue().get("myClassList");
//            synchronized (this) {
//                if (null == myClassList) {
//                    myClassList = myClassService.getList();
//                    redisTemplate.opsForValue().set("myClassList", myClassList);
//                }
//            }
//        }
        Boolean flag2 = redisLockUtil.lock("lock2", 120);
        if(null==myClassList) {
            if (flag2) {
                myClassList = myClassService.getList();
                redisTemplate.opsForValue().set("myClassList", myClassList);
                redisTemplate.expire("myClassList", 5, TimeUnit.MINUTES);
                redisLockUtil.unlock("lock2");
            } else {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return this.getStuAndClassList();
            }
        }
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
    public void add(Student student) {
        studentMapper.insert(student);
        redisTemplate.delete("studentList");
        redisTemplate.delete("myClassList");
    }

    @Override
    public StudentVO selectById(Integer id) {
        Student student = studentMapper.selectById(id);
        final MyClass myClass = myClassService.selectById(student.getCid());
        StudentVO studentVO=new StudentVO();
        BeanUtils.copyProperties(student, studentVO);
        studentVO.setMyClass(myClass);
        return studentVO;
    }

    @Override
    public void updateById(Student student) {
        studentMapper.updateById(student);
        redisTemplate.delete("studentList");
        redisTemplate.delete("myClassList");
    }

    @Override
    public void delete(Integer id) {
        studentMapper.deleteById(id);
        redisTemplate.delete("studentList");
        redisTemplate.delete("myClassList");
    }
}
