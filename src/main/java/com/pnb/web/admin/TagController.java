package com.pnb.web.admin;


import com.pnb.po.Tag;
import com.pnb.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){

        model.addAttribute("page",tagService.listTag(pageable));
        return "admin/tags";
    }

    //添加
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";

    }

    //编辑（更新）
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags-input";//返回增加分类的页面
    }

    //删除
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";

    }


    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag tag1=tagService.getTagByName(tag.getName());
        if(tag1!=null){
            result.rejectValue("name","nameError","您已添加该分类，不能重复添加");
        }

        if(result.hasErrors()){
            return "admin/tags-input";
        }

        Tag tt=tagService.saveTag(tag);
        if(tt==null){
            attributes.addFlashAttribute("message","添加失败");

        }else {
            attributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/tags";
    }


    //编辑
    @PostMapping("/tags/{id}")//Tag tag, BindingResult result一定要放一起
    public String editPost(@Valid Tag tag, BindingResult result,@PathVariable Long id, RedirectAttributes attributes) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        Tag t = tagService.updateTag(id,tag);
        if (t == null ) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/tags";
    }



}
