package service;

import entity.MyClass;
import entity.Student;

import java.util.List;

public interface IMyClassService {
    public List<MyClass> getList();

    void add(MyClass myClass);

    MyClass selectById(int cid);

}
