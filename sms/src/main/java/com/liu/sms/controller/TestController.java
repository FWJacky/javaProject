package com.liu.sms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName TestController
 * @Description TODO
 * @Author L
 * @Date 2019/8/31 14:02
 * @Version 1.0
 **/

@Controller
public class TestController {

    @RequestMapping("/test")
    public String test() {
        return "hello";
    }
}
