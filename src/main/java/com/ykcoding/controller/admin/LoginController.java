package com.ykcoding.controller.admin;

import com.ykcoding.Service.userService;
import com.ykcoding.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private userService userservice;

    @RequestMapping
    public String loginPage(){
        return "admin/login";

    }
    /*
    用户登陆功能
     */
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes attributes){
        User u = userservice.login(username,password);
        if(u!=null){
            //登陆成功注意session域密码值设空
            u.setPassword(null);
            session.setAttribute("user",u);
            return "admin/index";
        }else{
            attributes.addFlashAttribute("message","用户名密码错误");
            return "redirect:/admin";
        }
    }
    /*
    用户退出功能
     */
    @GetMapping("exit")
    public String exit(HttpSession session){
        session.invalidate();
        return "redirect:/admin";
    }

}
