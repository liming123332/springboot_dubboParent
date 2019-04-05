package com.dubbo.stu_web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import entity.MyClass;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import service.IMyClassService;

@Controller
@RequestMapping("myclass")
public class MyClassController {

    @Reference
    private IMyClassService myClassService;

    @RequestMapping("/toAdd")
    public String toAdd(){
        return "myclassAdd";
    }

    @RequestMapping("/add")
    public String add(MyClass myClass){
        myClassService.add(myClass);
        return "redirect:/student/getList";
    }
}
