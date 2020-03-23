package com.ykcoding.controller.admin;


import com.ykcoding.Service.blogService;

import com.ykcoding.Service.tagService;
import com.ykcoding.Service.typeService;
import com.ykcoding.po.BlogQuery;
import com.ykcoding.pojo.Blog;
import com.ykcoding.pojo.Tag;
import com.ykcoding.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {
    @Autowired
    private blogService blogservice;
    @Autowired
    private typeService typeservice;
    @Autowired
    private tagService  tagservice;
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 3,sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model){
        model.addAttribute("page",blogservice.ListBlog(pageable,blog));
        model.addAttribute("types",typeservice.ListType());
        return "admin/blogs";
    }
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 3,sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model){

        model.addAttribute("page",blogservice.ListBlog(pageable,blog));

        return "admin/blogs :: blogList";
    }


    @PostMapping("/blogs")
    public String add(@Validated Blog blog, RedirectAttributes attributes, HttpSession session){

        blog.setUser((User) session.getAttribute("user"));
        blog.setTags(tagservice.ListTag(blog.getTagIds()));
        blog.setType(typeservice.getType(blog.getType().getId()));
        Blog b;
        if (blog.getId() == null) {
            b =  blogservice.saveBlog(blog);
        } else {
            b = blogservice.updateBlog(blog,blog.getId());
        }

        if(b==null){
            attributes.addFlashAttribute("message","增加失败");
        }else{
            attributes.addFlashAttribute("message","增加成功");
        }
        return "redirect:/admin/blogs";
    }
    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("blog",new Blog());

        setTypeAndTag(model);
        return "admin/blogs-input";
    }
    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeservice.ListType());
        model.addAttribute("tags", tagservice.ListTag());
    }
    @GetMapping("blogs/{id}/input")
    public String  editInput(@PathVariable Long id, Model model){
        setTypeAndTag(model);

        Blog blog = blogservice.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return "admin/blogs-input";

    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogservice.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");

        return "redirect:/admin/blogs";

    }
}
