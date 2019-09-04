package com.liu.sms.mapper;

import com.liu.sms.po.PagingVO;
import com.liu.sms.po.TeacherCustom;

import java.util.List;


public interface TeacherMapperCustom {

    //分页查询老师信息
    List<TeacherCustom> findByPaging(PagingVO pagingVO) throws Exception;

}
