package com.nowcoder.controller;

import com.nowcoder.service.WendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @Author: wangyang
 * @Description:
 * @Date: Cread in 15:52 2017/9/12
 * @Modified By:
 */

@Controller
public class SettingComntroller1 {
    @Autowired
    WendaService wendaService;

    @RequestMapping(path = {"/setting2"}, method = {RequestMethod.GET})
    @ResponseBody
    public String setting(HttpSession httpSession) {
        return "王洋中彩票了. " + wendaService.getMessage(1);
    }
}
