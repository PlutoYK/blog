package com.ykcoding.controller;

import com.ykcoding.Service.blogService;
import com.ykcoding.Service.commentService;
import com.ykcoding.pojo.Comment;
import com.ykcoding.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {
    @Autowired
    private commentService  commentservice;
    @Autowired
    private blogService blogservice;
//    取出头像地址
    @Value("${comment.avatar}")
    private String avatar;

    @RequestMapping("/comments/{blogId}")
    public String CommentList(@PathVariable Long blogId, Model model){
    model.addAttribute("comments",commentservice.ListCommentByBlogId(blogId));
    return "blog :: commentList";
    }
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        User user = (User) session.getAttribute("user");
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogservice.getBlog(blogId));
        if(user!=null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);

        }else{
            comment.setAvatar(avatar);
        }

        commentservice.save(comment);

        return "redirect:/comments/"+blogId;
    }


}


