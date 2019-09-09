package com.liu.sms.controller;

import com.liu.sms.exception.CustomException;
import com.liu.sms.po.*;
import com.liu.sms.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName StudentController
 * @Description TODO
 * @Author L
 * @Date 2019/9/4 21:55
 * @Version 1.0
 **/

@Controller
@RequestMapping(value = "/student")
public class StudentController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserloginService userloginService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SelectedCourseService selectedCourseService;

    // 显示课程信息
    @RequestMapping(value = "/showCourse")
    public String stuCourseShow(Model model, Integer page) throws Exception {
        List<CourseCustom> list = null;
        // 页码对象
        PagingVO pagingVO = new PagingVO();
        // 设置总页数
        pagingVO.setTotalCount(courseService.getCountCourse());
        if (page == null || page == 0) {
            pagingVO.setToPageNo(1);
            list = courseService.findByPaging(1);
        } else {
            pagingVO.setToPageNo(page);
            list = courseService.findByPaging(page);
        }
        for (CourseCustom courseCustom : list) {
            TeacherCustom teacherCustom = teacherService.findById(courseCustom.getTeacherid());
            if (teacherCustom != null) {
                courseCustom.setTeacherName(teacherCustom.getUsername());
            }
        }
        model.addAttribute("courseList", list);
        model.addAttribute("pagingVO", pagingVO);
        return "/student/showCourse";
    }

    // 选课操作
    @RequestMapping(value = "/stuSelectedCourse")
    public String stuSelectedCourse(int id) throws Exception {
        // 获取当前用户名
        Subject subject = SecurityUtils.getSubject();
        Userlogin userlogin = (Userlogin) subject.getSession().getAttribute("user");

        SelectedCourseCustom selectedCourseCustom = new SelectedCourseCustom();
        selectedCourseCustom.setCourseid(id);
        selectedCourseCustom.setStudentid(Integer.valueOf(userlogin.getUsername()));


        SelectedCourseCustom s = selectedCourseService.findOne(selectedCourseCustom);

        if (s == null) {
            selectedCourseService.save(selectedCourseCustom);
        } else {
            throw new CustomException("此课程已选，不能再选。");
        }

        return "redirect:/student/selectedCourse";
    }

    // 退课操作
    @RequestMapping("/outCourse")
    public String outCourse(int id) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        Userlogin userlogin = (Userlogin) subject.getSession().getAttribute("user");
        SelectedCourseCustom selectedCourseCustom = new SelectedCourseCustom();
        selectedCourseCustom.setCourseid(id);
        selectedCourseCustom.setStudentid(Integer.valueOf(userlogin.getUsername()));

        selectedCourseService.remove(selectedCourseCustom);

        return "redirect:/student/selectedCourse";
    }

    // 已选课程
    @RequestMapping("/selectedCourse")
    public String selectedCourse(Model model) throws Exception {
        // 获取当前用户
        Subject subject = SecurityUtils.getSubject();
        Userlogin userlogin = (Userlogin) subject.getSession().getAttribute("user");

        StudentCustom studentCustom = studentService.findStudentAndSelectCourseListByName(userlogin.getUsername());

        List<SelectedCourseCustom> list = studentCustom.getSelectedCourseList();

        for (SelectedCourseCustom selectedCourseCustom : list) {
            CourseCustom courseCustom = selectedCourseCustom.getCouseCustom();
            TeacherCustom teacherCustom = teacherService.findById(courseCustom.getTeacherid());
            if (teacherCustom != null) {
                courseCustom.setTeacherName(teacherCustom.getUsername());
            }
        }

        model.addAttribute("selectedCourseList", list);

        return "/student/selectCourse";
    }

    // 已修课程
    @RequestMapping("/overCourse")
    public String overCourse(Model model) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        Userlogin userlogin = (Userlogin) subject.getSession().getAttribute("user");
        StudentCustom studentCustom = studentService.findStudentAndSelectCourseListByName(userlogin.getUsername());
        List<SelectedCourseCustom> list = studentCustom.getSelectedCourseList();
        for (SelectedCourseCustom selectedCourseCustom : list) {
            CourseCustom courseCustom = selectedCourseCustom.getCouseCustom();
            TeacherCustom teacherCustom = teacherService.findById(courseCustom.getTeacherid());
            if(teacherCustom != null) {
                courseCustom.setTeacherName(teacherCustom.getUsername());
            }
        }
        model.addAttribute("selectedCourseList", list);
        return "/student/overCourse";
    }

    // 修改密码
    @RequestMapping(value = "/passwordRest", method = {RequestMethod.GET})
    public String passwordRestUI() throws Exception {
        return "/student/passwordRest";
    }

    // 真实本账户密码重置业务
    @RequestMapping(value = "/passwordRest", method = {RequestMethod.POST})
    public String passwordRest(HttpServletRequest request, Model model) throws Exception {
        String password = request.getParameter("password1");
        System.out.println(password);
        Subject subject = SecurityUtils.getSubject();
        Userlogin userlogin = (Userlogin) subject.getSession().getAttribute("user");
        if (userlogin != null) {
            userlogin.setPassword(password);
            boolean flag = userloginService.resetPassword(userlogin);
            if (!flag) {
                model.addAttribute("message", "密码充值失败。");
                return "/error";
            }
        } else {
            throw new CustomException("此用户不存在。");
        }
        return "redirect:/login";
    }
}

