package com.ykcoding.controller.admin;


import com.ykcoding.Service.tagService;
import com.ykcoding.pojo.Tag;
import com.ykcoding.pojo.Type;
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

@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private tagService tagservice;
    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 8,sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page",tagservice.ListTag(pageable));

        return "admin/tags";
    }
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    @PostMapping("/tags")
    public String add(@Validated Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag tag1 = tagservice.getTagByName(tag.getName());
        if(tag1!=null){
            result.rejectValue("name","nameError","不能添加重复标签");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Tag t= tagservice.saveTag(tag);
        if(t==null){
            attributes.addFlashAttribute("message","增加失败");
        }else{
            attributes.addFlashAttribute("message","增加成功");
        }
        return "redirect:/admin/tags";
    }
    @GetMapping("tags/{id}/input")
    public String  editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagservice.getTag(id));
        return "admin/tags-input";

    }
    @PostMapping("/tags/{id}")
    public String edit(@Validated Tag tag, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){
        Tag tag1 = tagservice.getTagByName(tag.getName());
        if(tag1!=null){
            result.rejectValue("name","nameError","不能添加重复标签");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Tag t= tagservice.updateTag(id,tag);
        if(t==null){
            attributes.addFlashAttribute("message","更新失败");
        }else{
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/tags";
    }
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        tagservice.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");

        return "redirect:/admin/tags";

    }
}
