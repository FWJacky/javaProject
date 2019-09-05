package com.liu.sms.service.impl;

import com.liu.sms.mapper.CollegeMapper;
import com.liu.sms.po.College;
import com.liu.sms.po.CollegeExample;
import com.liu.sms.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CollegeServiceImpl implements CollegeService {

    @Autowired
    private CollegeMapper collegeMapper;

    @Override
    public List<College> finAll() throws Exception {
        CollegeExample collegeExample = new CollegeExample();
        CollegeExample.Criteria criteria = collegeExample.createCriteria();

        criteria.andCollegeidIsNotNull();

        return collegeMapper.selectByExample(collegeExample);
    }
}
