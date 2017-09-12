package com.nowcoder.controller;

import com.nowcoder.aspect.LogAspect;
import com.nowcoder.model.User;
import com.nowcoder.service.WendaService;
import com.sun.deploy.net.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.net.ssl.HttpsURLConnection;
import javax.print.DocFlavor;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by nowcoder on 2016/7/10.
 */
@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    WendaService wendaService;

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET})
    @ResponseBody
    public String index(HttpSession httpSession) {
        logger.info("VISIT HOME");
        return wendaService.getMessage(2) + "Hello NowCoder" + httpSession.getAttribute("msg");
    }

    @RequestMapping(path = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,
                          @PathVariable("groupId") String groupId,
                          @RequestParam(value = "type", defaultValue = "1") int type,
                          @RequestParam(value = "key", required = false) String key) {
        return String.format("Profile Page of %s / %d, t:%d k: %s", groupId, userId, type, key);
    }

    @RequestMapping(path = {"/vm"})
        public String template(Model model){
            //
            model.addAttribute("vlaue1","vwangyang1");

            List<String> colors = Arrays.asList(new String[]{"RED", "GREEN", "BLUE"});
            model.addAttribute("colors", colors);

            Map<String,String> map = new HashMap<>();
            for(int i=0;i< 4;++i){
                map.put(String.valueOf(i),String.valueOf(i*i));
            }
            model.addAttribute("map",map);
            model.addAttribute("user",new User("王洋"));
        return "home";
    }

    @RequestMapping(path = {"/request"},method = {RequestMethod.GET})
    @ResponseBody
    public String request(Model model, HttpServletRequest request ,
                          HttpServletResponse response,HttpSession httpSessionsession,
                          @CookieValue("JSESSIONID") String sessionId) {

        StringBuilder sb=new StringBuilder();
        sb.append("cookievalue:"+sessionId + "<br>");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            sb.append(name +":"+request.getHeader(name) + "<br>");
        }
        if(request.getCookies() != null){
            for(Cookie cookie:request.getCookies()){
                sb.append("cookie" + cookie.getName() +":"+ "value" + cookie.getValue()+"<br>");
            }
        }
        sb.append(request.getMethod() + "<br>");
        sb.append(request.getQueryString() + "<br>");
        sb.append(request.getPathInfo() + "<br>");
        sb.append(request.getRequestURI() + "<br>");

        response.addHeader("newcode","hello");
        response.addCookie(new Cookie("username","newcoder"));
            return sb.toString();
    }

    @RequestMapping(path = {"/redirect/{code}"},method = {RequestMethod.GET})
    public RedirectView redirect(@PathVariable("code") int code,HttpSession httpSession){
            httpSession.setAttribute("msg","王洋发财了，长命百岁，身体健康");
             RedirectView red = new RedirectView("/",true);
             if(code==301){
                 red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
             }
            return red;
    }
    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e){
        return  "error:" + e.getMessage();
    }

    @RequestMapping(path = {"/admin"}, method = {RequestMethod.GET})
    @ResponseBody
    public String admin(@RequestParam("key") String key ){
        if("admin".equals(key)){
            return  "hello admin";
        }
        throw new IllegalArgumentException("参数不对");
    }

}
