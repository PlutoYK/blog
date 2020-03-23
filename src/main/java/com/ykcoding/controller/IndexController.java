package com.ykcoding.controller;

import com.ykcoding.Service.blogService;
import com.ykcoding.Service.tagService;
import com.ykcoding.Service.typeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {
    @Autowired
    private blogService blogservice;
    @Autowired
    private typeService typeservice;
    @Autowired
    private tagService  tagservice;




    @RequestMapping("/")
    public String index(@PageableDefault( size=8,sort={"updateTime"},direction = Sort.Direction.DESC)Pageable pageable, Model model){
        model.addAttribute("page",blogservice.ListBlog(pageable));
        model.addAttribute("types",typeservice.ListTypeTop(6));
        model.addAttribute("tags",tagservice.ListTagTop(6));
        model.addAttribute("recommendBlogs",blogservice.ListReCommendBlogTop(8));
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault( size=8,sort={"updateTime"},direction = Sort.Direction.DESC)Pageable pageable, @RequestParam String query, Model model){
        model.addAttribute("page",blogservice.query(pageable,"%"+query+"%"));
        model.addAttribute("query",query);
        return "search";

    }
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        model.addAttribute("blog",blogservice.getAndconvert(id));
        return "blog";
    }
    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        model.addAttribute("newblogs", blogservice.ListReCommendBlogTop(3));
        return "_fragment :: newblogList";
    }
}
