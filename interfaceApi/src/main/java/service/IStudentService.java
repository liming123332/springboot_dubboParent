package service;

import entity.Student;
import entity.StudentVO;

import java.util.List;

public interface IStudentService {
    public List<Student> getList();

    public List<StudentVO> getStuAndClassList();

    void add(Student student);

    StudentVO selectById(Integer id);

    void updateById(Student student);

    void delete(Integer id);
}
