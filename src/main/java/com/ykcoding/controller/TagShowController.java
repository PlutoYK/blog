package com.ykcoding.controller;

import com.ykcoding.Service.blogService;
import com.ykcoding.Service.tagService;

import com.ykcoding.po.BlogQuery;
import com.ykcoding.pojo.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagShowController {
    @Autowired
    private tagService  tagservice;
    @Autowired
    private blogService blogservice;

    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size=8,sort={"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model){
        List<Tag> tags = tagservice.ListTagTop(10000);
        if(id ==-1){
            id = tags.get(0).getId();
        }

        model.addAttribute("tags",tags);
        model.addAttribute("page",blogservice.listBlog(id,pageable));
        model.addAttribute("activeType",id);
        return "tags";
    }
}
