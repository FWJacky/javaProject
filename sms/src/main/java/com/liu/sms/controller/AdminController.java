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
 * 管理员控制器
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CollegeService collegeService;

    @Autowired
    private UserloginService userloginService;

    /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<学生操作>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/

    // 显示学生信息
    @RequestMapping("/showStudent")
    public String showStudentList(Model model, Integer page) throws Exception {
        List<StudentCustom> list = null;
        // 获取页面对象
        PagingVO pagingVO = new PagingVO();
        // 设置总页数
        pagingVO.setTotalCount(studentService.getCountStudent());
        if (page == null || page == 0) {
            pagingVO.setToPageNo(1);
            list = studentService.findByPaging(1);
        } else {
            pagingVO.setToPageNo(page);
            list = studentService.findByPaging(page);
        }
        model.addAttribute("studentList", list);
        model.addAttribute("pagingVO", pagingVO);

        return "/admin/showStudent";
    }

    // 显示添加学生信息的页面
    @RequestMapping(value = "/addStudent", method = {RequestMethod.GET})
    public String addStudentUI(Model model) throws Exception {
        List<College> list = collegeService.finAll();
        model.addAttribute("collegeList", list);
        return "/admin/addStudent";
    }

    // 真实添加学生信息操作
    @RequestMapping(value = "/addStudent", method = {RequestMethod.POST})
    public String addStudent(StudentCustom studentCustom, Model model) throws Exception {
        Boolean flag = studentService.save(studentCustom);
        if (!flag) {
            model.addAttribute("message", "学号重复");
            return "/error";
        }
        // 添加成功后，也添加到登录表中
        Userlogin userlogin = new Userlogin();
        userlogin.setUsername(studentCustom.getUserid().toString());
        // 所有新增用户默认登录密码为123
        userlogin.setPassword("123");
        // 学生的权限等级为2
        userlogin.setRole(2);
        userloginService.save(userlogin);
        // 重定向
        return "redirect:/admin/showStudent";
    }

    // 显示修改学生信息页面
    @RequestMapping(value = "/editStudent", method = {RequestMethod.GET})
    public String editStudentUI(Integer id, Model model) throws Exception {
        if (id == null) {
            // 如果没有获取到表单传递来的id，那就说明没有进行添加，返回至学生显示页面
            return "redirect:/admin/showStudent";
        }
        StudentCustom studentCustom = studentService.findById(id);
        if (studentCustom == null) {
            throw new CustomException("未找到此学生。");
        }
        List<College> list = collegeService.finAll();
        model.addAttribute("collegeList", list);
        model.addAttribute("student", studentCustom);
        return "/admin/editStudent";
    }

    // 真实修改学生信息业务
    @RequestMapping(value = "/editStudent", method = {RequestMethod.POST})
    public String editStudent(StudentCustom studentCustom) throws Exception {
        studentService.updataById(studentCustom.getUserid(), studentCustom);
        // 重定向
        return "redirect:/admin/showStudent";
    }

    // 删除学生业务
    @RequestMapping(value = "removeStudent", method = {RequestMethod.GET})
    public String removeStudent(Integer id) throws Exception {
        if (id == null) {
            // 没有接收到页面传递的id
            return "/admin/showStudent";
        }
        studentService.removeById(id);
        userloginService.removeByName(id.toString());
        return "redirect:/admin/showStudent";
    }

    // 搜索学生业务----模糊查询
    @RequestMapping(value = "/selectStudent", method = {RequestMethod.POST})
    public String selectStudent(String username, Model model) throws Exception {
        List<StudentCustom> list = studentService.findByName(username);
        model.addAttribute("studentList", list);
        return "/admin/showStudent";
    }


    /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<教师操作>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/

    // 显示教师页面
    @RequestMapping(value = "/showTeacher")
    public String showTeacher(Model model, Integer page) throws Exception {
        List<TeacherCustom> list = null;
        // 页码对象
        PagingVO pagingVO = new PagingVO();
        // 设置总页数
        pagingVO.setTotalCount(teacherService.getCountTeacher());
        if (page == null || page == 0) {
            pagingVO.setToPageNo(1);
            list = teacherService.findByPaging(1);
        } else {
            pagingVO.setToPageNo(page);
            list = teacherService.findByPaging(page);
        }
        model.addAttribute("teacherList", list);
        model.addAttribute("pagingVO", pagingVO);
        return "/admin/showTeacher";
    }

    // 显示添加教师信息页面
    // 这一步是将所有的专业信息查询出来传至添加教师信息的页面，供添加时选择教师所属的系
    @RequestMapping(value = "/addTeacher", method = {RequestMethod.GET})
    public String addTeacherUI(Model model) throws Exception {
        List<College> list = collegeService.finAll();
        model.addAttribute("collegeList", list);
        return "/admin/addTeacher";
    }

    // 真实添加教师信息业务
    @RequestMapping(value = "/addTeacher", method = {RequestMethod.POST})
    public String addTeacher(TeacherCustom teacherCustom, Model model) throws Exception {
        Boolean flag = teacherService.save(teacherCustom);
        if (!flag) {
            model.addAttribute("message", "工号重复。");
            return "error";
        }
        // 添加教师信息成功后，将所属信息添加到登录表中
        Userlogin userlogin = new Userlogin();
        userlogin.setUsername(teacherCustom.getUserid().toString());
        // 默认密码为123
        userlogin.setPassword("123");
        // 教师的权限等级为1
        userlogin.setRole(1);
        userloginService.save(userlogin);
        // 重定向
        return "redirect:/admin/showTeacher";
    }

    // 显示修改老师信息页面
    @RequestMapping(value = "/editTeacher", method = {RequestMethod.GET})
    public String editTeacherUI(Integer id, Model model) throws Exception {
        if (id == null) {
            return "redirect:/admin/showTeacher";
        }
        TeacherCustom teacherCustom = teacherService.findById(id);
        if (teacherCustom == null) {
            throw new CustomException("未找到此教师。");
        }
        List<College> list = collegeService.finAll();
        model.addAttribute("collegeList", list);
        model.addAttribute("teacher", teacherCustom);
        return "/admin/editTeacher";
    }

    // 真实修改教师信息业务
    @RequestMapping(value = "/editTeacher", method = {RequestMethod.POST})
    public String editTeacher(TeacherCustom teacherCustom) throws Exception {
        teacherService.updateById(teacherCustom.getUserid(), teacherCustom);
        return "redirect:/admin/showTeacher";
    }

    // 删除教师
    @RequestMapping(value = "/removeTeacher")
    public String removeTeacher(Integer id) throws Exception {
        if (id == null) {
            return "/admin/showTeacher";
        }
        teacherService.removeById(id);
        userloginService.removeByName(id.toString());
        return "redirect:/admin/showTeacher";
    }

    // 搜索教师----模糊查询
    @RequestMapping(value = "/selectTeacher", method = {RequestMethod.POST})
    public String selectTeacher(String username, Model model) throws Exception {
        List<TeacherCustom> list = teacherService.findByName(username);
        model.addAttribute("teacherList", list);
        return "/admin/showTeacher";
    }

    /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<课程操作>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/

    // 显示课程信息页面
    @RequestMapping("/showCourse")
    public String showCourse(Model model, Integer page) throws Exception {
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
        return "/admin/showCourse";
    }

    // 显示添加课程页面
    @RequestMapping(value = "/addCourse", method = {RequestMethod.GET})
    public String addCourseUI(Model model) throws Exception {
        List<TeacherCustom> list = teacherService.findAll();
        List<College> collegeList = collegeService.finAll();
        model.addAttribute("teacherList", list);
        model.addAttribute("collegeList", collegeList);
        return "/admin/addCourse";
    }

    // 真实添加课程业务
    @RequestMapping(value = "/addCourse", method = {RequestMethod.POST})
    public String addCourse(CourseCustom courseCustom, Model model) throws Exception {
        Boolean flag = courseService.save(courseCustom);
        if (!flag) {
            model.addAttribute("message", "课程编号重复。");
            return "/error";
        }
        // 重定向
        return "redirect:/admin/showCourse";
    }

    // 显示修改课程信息页面
    @RequestMapping(value = "/editCourse", method = {RequestMethod.GET})
    public String editCourseUI(Integer id, Model model) throws Exception {
        if (id == null) {
            return "redirect:/admin/showCourse";
        }
        CourseCustom courseCustom = courseService.findById(id);
        if (courseCustom == null) {
            throw new CustomException("未找到次课程信息。");
        }
        List<TeacherCustom> list = teacherService.findAll();
        List<College> collegeList = collegeService.finAll();
        model.addAttribute("teacherList", list);
        model.addAttribute("collegeList", collegeList);
        model.addAttribute("course", courseCustom);
        return "/admin/editCourse";
    }

    // 真实修改课程信息页面
    @RequestMapping(value = "/editCourse", method = {RequestMethod.POST})
    public String editCourse(CourseCustom courseCustom) throws Exception {
        courseService.upadteById(courseCustom.getCourseid(), courseCustom);
        return "redirect:/admin/showCourse";
    }

    // 删除课程
    @RequestMapping("/removeCourse")
    public String removeCourse(Integer id) throws Exception {
        if (id == null) {
            return "/admin/showCourse";
        }
        courseService.removeById(id);
        return "redirect:/admin/showCourse";
    }

    // 搜索课程----模糊查询
    @RequestMapping(value = "/selectCourse", method = {RequestMethod.POST})
    public String selectCourse(String courseName, Model model) throws Exception {
        List<CourseCustom> list = courseService.findByName(courseName);
        for (CourseCustom courseCustom : list) {
            TeacherCustom teacherCustom = teacherService.findById(courseCustom.getTeacherid());
            if (teacherCustom != null) {
                courseCustom.setTeacherName(teacherCustom.getUsername());
            }
        }
        model.addAttribute("courseList", list);
        return "/admin/showCourse";
    }

    /*<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<其他操作>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/

    // 显示普通用户账户密码重置页面
    @RequestMapping(value = "/userPasswordRest", method = {RequestMethod.GET})
    public String userPasswordRestUI() throws Exception {
        return "/admin/userPasswordRest";
    }

    // 真实普通用户账户密码重置业务
    @RequestMapping(value = "/userPasswordRest", method = {RequestMethod.POST})
    public String userPasswordRest(Userlogin userlogin) throws Exception {
        Userlogin userlogin1 = userloginService.findByName(userlogin.getUsername());
        if (userlogin1 != null) {
            // 管理员密码没有权限修改
            if (userlogin1.getRole() == 0) {
                throw new CustomException("此账户为管理员，无法重置密码。");
            }
            userlogin1.setPassword(userlogin.getPassword());
            userloginService.updateByName(userlogin.getUsername(), userlogin1);
        } else {
            throw new CustomException("此用户不存在。");
        }
        return "/admin/userPasswordRest";
    }

    // 显示本账户密码重置页面
    @RequestMapping(value = "/passwordRest", method = {RequestMethod.GET})
    public String passwordRestUI() throws Exception {
        return "/admin/passwordRest";
    }

    // 真实本账户密码重置业务
    @RequestMapping(value = "/passwordRest", method = {RequestMethod.POST})
    public String passwordRest(HttpServletRequest request,Model model) throws Exception {
        String password = request.getParameter("password1");
        System.out.println(password);
        Subject subject = SecurityUtils.getSubject();
        Userlogin userlogin = (Userlogin) subject.getSession().getAttribute("user");
        if (userlogin != null) {
            userlogin.setPassword(password);
            boolean flag = userloginService.resetPassword(userlogin);
            if(!flag) {
                model.addAttribute("message","密码充值失败。");
                return "/error";
            }
        } else {
            throw new CustomException("此用户不存在。");
        }
        return "redirect:/login";
    }

    // 返回课程总数
    @RequestMapping(value = "/menu",method = {RequestMethod.POST})
    public String count(Model model) throws Exception {
        int courseCount = courseService.getCountCourse();
        int studentCount = studentService.getCountStudent();
        int teacherCount = teacherService.getCountTeacher();
        model.addAttribute("course",courseCount);
        model.addAttribute("student",studentCount);
        model.addAttribute("teacher",teacherCount);
        return "/admin/menu";
    }
}
