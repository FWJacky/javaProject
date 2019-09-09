package com.liu.sms.util;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName DateConvert
 * @Description TODO
 * @Author L
 * @Date 2019/8/31 14:14
 * @Version 1.0
 **/
public class DateConvert implements Converter<String, Date> {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public Date convert(String source) {

        try {
            return simpleDateFormat.parse(source);
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
