package com.liu.sms.controller;

import com.liu.sms.exception.CustomException;
import com.liu.sms.po.CourseCustom;
import com.liu.sms.po.SelectedCourseCustom;
import com.liu.sms.po.TeacherCustom;
import com.liu.sms.po.Userlogin;
import com.liu.sms.service.CourseService;
import com.liu.sms.service.SelectedCourseService;
import com.liu.sms.service.TeacherService;
import com.liu.sms.service.UserloginService;
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
 * @ClassName TeacherController
 * @Description TODO
 * @Author L
 * @Date 2019/9/5 20:17
 * @Version 1.0
 **/

@Controller
@RequestMapping(value = "/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private SelectedCourseService selectedCourseService;

    @Autowired
    private UserloginService userloginService;

    // 显示我的课程
    @RequestMapping(value = "/showCourse")
    public String stuCourseShow(Model model) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        List<CourseCustom> list = courseService.findByTeacherID(Integer.valueOf(username));
        for (CourseCustom courseCustom : list) {
            TeacherCustom teacherCustom = teacherService.findById(courseCustom.getTeacherid());
            if(teacherCustom!=null) {
                courseCustom.setTeacherName(teacherCustom.getUsername());
            }
        }
        model.addAttribute("courseList", list);
        return "/teacher/showCourse";
    }

    // 搜索课程----模糊查询
    @RequestMapping(value = "/selectCourse", method = {RequestMethod.POST})
    public String selectCourse(String courseName, Model model) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        Userlogin userlogin = (Userlogin) subject.getSession().getAttribute("user");
        System.out.println(userlogin.getUsername());
        CourseCustom courseCustom = new CourseCustom();
        courseCustom.setTeacherID(Integer.valueOf(userlogin.getUsername()));
        courseCustom.setCoursename(courseName);
        List<CourseCustom> list = courseService.findByCourseWithTeacher(courseCustom);
        for (CourseCustom courseCustom1 : list) {
            TeacherCustom teacherCustom = teacherService.findById(Integer.valueOf(userlogin.getUsername()));
            if (teacherCustom != null) {
                courseCustom1.setTeacherName(teacherCustom.getUsername());
            }
        }
        model.addAttribute("courseList", list);
        return "/teacher/showCourse";
    }

    // 显示成绩
    @RequestMapping(value = "/gradeCourse")
    public String gradeCourse(Integer id, Model model) throws Exception {
        if (id == null) {
            return "";
        }
        List<SelectedCourseCustom> list = selectedCourseService.findByCourseID(id);
        model.addAttribute("selectedCourseList", list);
        return "/teacher/showGrade";
    }

    // 显示评分页面
    @RequestMapping(value = "/mark", method = {RequestMethod.GET})
    public String markUI(SelectedCourseCustom scc, Model model) throws Exception {
        SelectedCourseCustom selectedCourseCustom = selectedCourseService.findOne(scc);
        model.addAttribute("selectedCourse", selectedCourseCustom);
        return "/teacher/gradeCourse";
    }

    // 评分业务页面
    @RequestMapping(value = "/mark", method = {RequestMethod.POST})
    public String mark(SelectedCourseCustom scc) throws Exception {
        selectedCourseService.updateOne(scc);
        return "redirect:/teacher/showCourse";
    }

    // 修改密码
    @RequestMapping(value = "/passwordRest", method = {RequestMethod.GET})
    public String passwordRestUI() throws Exception {
        return "/teacher/passwordRest";
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
