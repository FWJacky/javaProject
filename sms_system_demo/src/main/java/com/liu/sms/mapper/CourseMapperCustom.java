package com.liu.sms.mapper;

import com.liu.sms.po.CourseCustom;
import com.liu.sms.po.PagingVO;

import java.util.List;


public interface CourseMapperCustom {

    //分页查询学生信息
    List<CourseCustom> findByPaging(PagingVO pagingVO) throws Exception;

    //根据课程名查找对应教师的课程
    List<CourseCustom> findByCourseWithTeacher(CourseCustom courseCustom) throws Exception;

}
