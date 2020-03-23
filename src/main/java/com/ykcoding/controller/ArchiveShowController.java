package com.ykcoding.controller;

import com.ykcoding.Service.blogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ArchiveShowController {

    @Autowired
    private blogService blogservice;

    @GetMapping("/archives")
    public String Archive( Model model){
        model.addAttribute("archiveMap",blogservice.ArchiveBlog());
        model.addAttribute("blogCount",blogservice.countBlog());
        return "archives";
    }
}
