package com.mituhan.shop.controller.admin;

import com.mituhan.shop.model.CategoryModel;
import com.mituhan.shop.model.UserModel;
import com.mituhan.shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //------------xem Category---------//
    @GetMapping("admin/category/list")
    public String list(Model model,
                       @RequestParam(defaultValue = "") Optional<String> Keyword,
                       @PageableDefault(size = 100) Pageable pageable){
        Page<CategoryModel> categorys = categoryService.findAllByNameContaining(Keyword,pageable);
        model.addAttribute("categorys", categorys);
        return "views/admin/category/index";
    }

    //------------thêm category--------//
    @GetMapping("admin/category/add")
    public String add(Model model,
                      @RequestParam(defaultValue = "") Optional<String> Keyword,
                      @PageableDefault(size = 100) Pageable pageable) {
        Page<CategoryModel> categorys = categoryService.findAllByNameContaining(Keyword,pageable);
        model.addAttribute("parents", categorys);
        model.addAttribute("gModel", new CategoryModel());
        return "views/admin/category/add";
    }

    @PostMapping("admin/category/doadd")
    public String doAdd(CategoryModel cModel, RedirectAttributes attributes, HttpSession session) {
        CategoryModel category = cModel;
        CategoryModel checkCategory = categoryService.findByName(category.getName());
        if(checkCategory == null){
            String rMessage = categoryService.saveCategory(category,session);
            if(rMessage == "success"){
                attributes.addFlashAttribute("message", "Add category successfully");
            }else{
                attributes.addFlashAttribute("error", "Add failed ");
            }
            return "redirect:/admin/category/add";
        }else{
            attributes.addFlashAttribute("error", "category name already exists");
            return "redirect:/admin/category/add";
        }
    }

    //------------sửa category---------//
    @GetMapping("admin/category/edit")
    public String edit(Model model, RedirectAttributes attributes, @RequestParam Long id,
                       @RequestParam(defaultValue = "") Optional<String> Keyword,
                       @PageableDefault(size = 100) Pageable pageable) {
        CategoryModel category = categoryService.findCategoryById(id);
        Page<CategoryModel> parents = (Page<CategoryModel>) categoryService.findAllByNameContaining(Keyword, pageable);
        if(category != null){
            model.addAttribute("category", category);
            model.addAttribute("parents", parents);
            return "views/admin/category/edit";
        }else{
            attributes.addFlashAttribute("message", "category does not exist ");
            return "views/admin/category/index";
        }


    }

    @PostMapping("admin/category/doedit")
    public String doEdit(CategoryModel cModel,
                         RedirectAttributes attributes, HttpSession session) {
        CategoryModel category =  cModel;
        CategoryModel checkUser = categoryService.findCategoryById(category.getId());
        if (checkUser != null){
            Long id = categoryService.editCategory(category,session);
            attributes.addFlashAttribute("message", "edit user successfully");
            return "redirect:/admin/user/edit?id=" + id;
        }else{
            attributes.addFlashAttribute("message", "Account does not exists");
            return "redirect:/admin/user/edit?id=" + category.getId();
        }
    }

    //------------xóa category---------//
    @GetMapping("admin/category/delete")
    public String delete(@RequestParam Long id, RedirectAttributes attributes) {
        attributes.addFlashAttribute("message", "deleted categories ");
        categoryService.deleteCategory(id);
        return "redirect:/admin/category/list";
    }
}
