package com.dubbo.stu_web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import entity.Student;
import entity.StudentVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import service.IStudentService;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Reference
    private IStudentService studentService;

    @RequestMapping("/getList")
    public String getList(Model model){

        List<StudentVO> students=studentService.getStuAndClassList();
        model.addAttribute("students",students);
        return "stuList";
    }

}
