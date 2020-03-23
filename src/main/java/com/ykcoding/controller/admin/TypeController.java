package com.ykcoding.controller.admin;

import com.ykcoding.Service.typeService;
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
public class TypeController {
    @Autowired
    private typeService typeservice;
    @GetMapping("/types")
    public String types(@PageableDefault(size = 8,sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page",typeservice.ListType(pageable));

        return "admin/types";
    }
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    @PostMapping("/types")
    public String add(@Validated Type type, BindingResult result, RedirectAttributes attributes){
        Type type1 = typeservice.getTypeByName(type.getName());
        if(type1!=null){
            result.rejectValue("name","nameError","不能添加重复分类");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type t= typeservice.saveType(type);
        if(t==null){
            attributes.addFlashAttribute("message","增加失败");
        }else{
            attributes.addFlashAttribute("message","增加成功");
        }
        return "redirect:/admin/types";
    }
    @GetMapping("types/{id}/input")
    public String  editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeservice.getType(id));
        return "admin/types-input";

    }
    @PostMapping("/types/{id}")
    public String edit(@Validated Type type, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){
        Type type1 = typeservice.getTypeByName(type.getName());
        if(type1!=null){
            result.rejectValue("name","nameError","不能添加重复分类");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type t= typeservice.updateType(id,type);
        if(t==null){
            attributes.addFlashAttribute("message","更新失败");
        }else{
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeservice.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");

        return "redirect:/admin/types";

    }
}
