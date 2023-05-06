package com.wpz.community.controller;

import com.wpz.community.service.AlphaService;
import com.wpz.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private AlphaService alphaService; // 在controller中调service

    @RequestMapping("/hello")
    @ResponseBody
    public String helloSpring(){
        return "Hello Spring!";
    }

    /**
     * 处理请求，从service层调用find方法, 将返回的结果映射到/data目录中去
     * @return
     */
    @RequestMapping("/data")
    @ResponseBody
    public String getData(){
        return alphaService.find();
    }

    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response){
        // 获取请求数据
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        Enumeration<String> enumeration = request.getHeaderNames(); // enumeration是一个习惯写法的迭代器
        while(enumeration.hasMoreElements()){
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ": " + value);
        }
        System.out.println(request.getParameter("code"));

        // 返回响应数据
        response.setContentType("text/html;charset=utf-8");
        try (
                PrintWriter writer = response.getWriter();
        ){
            writer.write(("<h1>牛客网</h1>"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //两种get请求
    // 第一种： /students?current=1&limit=20
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(@RequestParam(name = "current", required = false, defaultValue = "1") int current,
                              @RequestParam(name = "limit", required = false, defaultValue = "20") int limit){
        System.out.println(current);
        System.out.println(limit);

        return "some students";
    }

    // 第二种： /student/123
    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id){
        System.out.println(id); // 可以将路径中的id参数传入到方法里面

        return "a student";
    }

    // post请求, 将浏览器的数据传到服务器来
    // 页面响应
    @RequestMapping(path="/addStudent", method = RequestMethod.POST)
    @ResponseBody
    public String addStudent(String name, int age){ // 这里参数的名字与表单的name一致，就会自动传过来
        System.out.println(name);
        System.out.println(age);

        return "add success";
    }

    // 响应HTML数据
    // 第一种
    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "张三");
        mav.addObject("age", 30);
        mav.setViewName("/demo/view"); // 把mav获得的数据，装载进view.html中,最终会自动渲染并返回网页内容
        return mav;
    }

    // 更为简洁的一种方式
    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name", "电子科大");
        model.addAttribute("age", 80); // 这里的age要和thymleaf模板引擎里的age对应
        return "/demo/view"; // 这里没有用@ResponseBody，所以返回的字符串实际上是资源的位置，通过这种方式渲染
    }

    // 响应JSON数据(异步请求)
    // Java对象 -> JSON字符串 -> JS对象
    @RequestMapping(path = "/employee", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getEmps(){
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", 23);
        emp.put("salary", 10000);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "王五");
        emp.put("age", 24);
        emp.put("salary", 20000);
        list.add(emp);

        return list; // 如果有@ResponseBody，返回对象的话，就会自动转换为JSON字符串，供其他地方解析使用
    }

    // cookie示例
    @RequestMapping(path = "/cookie/set", method = RequestMethod.GET)
    @ResponseBody
    public String setCookie(HttpServletResponse response){ // cookie是服务器响应给浏览器的，并由浏览器（客户端）本地保存
        // 创建cookie
        Cookie cookie = new Cookie("code", CommunityUtil.generaterUUID());
        // 设置cookie生效的范围
        cookie.setPath("/community/hello");
        // 设置cookie的生存时间
//        cookie.setMaxAge(60 * 10); // 若不设置，则默认浏览器关闭，cookie到期
        // 服务器响应请求并发送cookie
        response.addCookie(cookie);

        return "本次请求由服务器设置了一个cookie发送给客户端";

    }

    @RequestMapping(path = "/cookie/get", method = RequestMethod.GET)
    @ResponseBody
    public String getCookie(@CookieValue("code") String code){
        System.out.println(code);

        return "客户端携带cookie向服务器发送请求";
    }

    // session示例
    @RequestMapping(path = "/session/set", method = RequestMethod.GET)
    @ResponseBody
    public String setSession(HttpSession session){ // session由Spring自动注入容器
        session.setAttribute("id", 1);
        session.setAttribute("name", "Test");

        return "用户请求使服务器在自己的内存中设置了一个session，并将sessionId附在cookie中返回给浏览器";
    }

    @RequestMapping(path = "/session/get", method = RequestMethod.GET)
    @ResponseBody
    public String getSession(HttpSession session){
        System.out.println(session.getAttribute("id"));
        System.out.println(session.getAttribute("name"));

        return "浏览器带着seesionId访问服务器，服务器返回相关信息";
    }

    // ajax示例, 在页面不刷新的情况下，向服务器请求或提交了一些结果
    @RequestMapping(path = "/ajax", method = RequestMethod.POST)
    @ResponseBody // 由于是异步请求，所以返回的不是网页而是字符串
    public String testAjax(String name, int age){
        System.out.println(name);
        System.out.println(age);

        return CommunityUtil.getJSONString(0, "操作成功");
    }
}
