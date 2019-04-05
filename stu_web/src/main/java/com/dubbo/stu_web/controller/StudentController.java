package com.dubbo.stu_web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import entity.MyClass;
import entity.Student;
import entity.StudentVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import service.IMyClassService;
import service.IStudentService;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Reference
    private IStudentService studentService;

    @Reference
    private IMyClassService myClassService;

    @RequestMapping("/getList")
    public String getList(Model model){
        List<StudentVO> students=studentService.getStuAndClassList();
        model.addAttribute("students",students);
        return "stuList";
    }

    @RequestMapping("/toAdd")
    public String toAdd(Model model){
        List<MyClass> myClassList = myClassService.getList();
        model.addAttribute("myClassList",myClassList);
        return "stuAdd";
    }

    @RequestMapping("/add")
    public String add(Student student){
        studentService.add(student);
        return "redirect:/student/getList";
    }

    @RequestMapping("/toUpdate/{id}")
    public String toUpdate(@PathVariable Integer id,Model model){
        StudentVO studentVO=studentService.selectById(id);
        List<MyClass> myClassList = myClassService.getList();
        model.addAttribute("myClassList",myClassList);
        model.addAttribute("stu", studentVO);
        return "stuUpdate";
    }

    @RequestMapping("/update")
    public String toUpdate(Student student){
        studentService.updateById(student);
        return "redirect:/student/getList";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        studentService.delete(id);
        return "redirect:/student/getList";
    }

}
