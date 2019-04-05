package service;

import entity.Student;
import entity.StudentVO;

import java.util.List;

public interface IStudentService {
    public List<Student> getList();

    public List<StudentVO> getStuAndClassList();
}
