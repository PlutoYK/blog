package com.ykcoding.controller;

import com.ykcoding.Service.blogService;
import com.ykcoding.Service.typeService;
import com.ykcoding.po.BlogQuery;
import com.ykcoding.pojo.Type;
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
public class TypeShowController {
    @Autowired
    private typeService typeservice;
    @Autowired
    private blogService blogservice;

    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size=8,sort={"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable  Long id, Model model){
        List<Type> types = typeservice.ListTypeTop(10000);
        if(id ==-1){
            id = types.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types",types);
        model.addAttribute("page",blogservice.ListBlog(pageable,blogQuery));
        model.addAttribute("activeType",id);
        return "types";
    }
}
