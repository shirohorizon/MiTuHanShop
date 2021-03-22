package com.mituhan.shop.controller.admin;

import com.mituhan.shop.model.RoleModel;
import com.mituhan.shop.model.UserModel;
import com.mituhan.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import java.io.File;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    //lấy danh sách user theo limit
    @GetMapping("admin/user/list")
    public String list(Model model,
                       @RequestParam(defaultValue = "") Optional<String> keyword,
                       @RequestParam(defaultValue = "") RoleModel role,
                       @PageableDefault(size = 100) Pageable pageable) {
        Page<UserModel> users;
        if(role == null){
            //lấy users theo keyword và phân trang nếu keyword = null -> findAll
            users = (Page<UserModel>) userService.findAllByUsernameContaining(keyword, pageable);
            model.addAttribute("users", users);
        }else {
            users = userService.findAllByRolesContaining(role, pageable);
            if(users.isEmpty()){
                model.addAttribute("users", users);
            }else{
                model.addAttribute("users", "");
            }
        }
        Page<RoleModel> roles = userService.fildRoleAll(pageable);
        model.addAttribute("roles", roles);
        return "views/admin/user/index";
    }

    //---------------thêm user------------------//
    @GetMapping("admin/user/add")
    public String add(Model model) {
        model.addAttribute("user", new UserModel());
        return "views/admin/user/add";
    }


    @PostMapping("admin/user/doadd")
    public String doAdd(UserModel gModel, RedirectAttributes attributes, @RequestParam(name = "file") MultipartFile file, HttpSession session) {
        UserModel user = gModel;
        UserModel checkUser = userService.findByUsername(user.getUsername());
        if (checkUser == null){
            String rMessage = userService.saveUser(user,file,session);
            if(rMessage == "success"){
                attributes.addFlashAttribute("message", "Add user successfully");
            }else if (rMessage == "error"){
                attributes.addFlashAttribute("error", "Add failed ");
            }
            return "redirect:/admin/user/add";
        }else {
            attributes.addFlashAttribute("message", "Account already exists");
            return "redirect:/admin/user/add";
        }
    }

    //-------------chỉnh sửa user---------------//
    @GetMapping("admin/user/edit")
    public String edit(Model model, RedirectAttributes attributes, @RequestParam Long id) {
        UserModel user = userService.findUserById(id);
        if(user != null){
            model.addAttribute("userEdit", user);
            return "views/admin/user/edit";
        }else{
            attributes.addFlashAttribute("message", "id does not exist ");
            return "views/admin/user/index";
        }


    }

    @PostMapping("admin/user/doedit")
    public String doEdit(UserModel gModel,
                         RedirectAttributes attributes, @RequestParam(name = "file") MultipartFile file, HttpSession session) {
        UserModel user = (UserModel) gModel;
        UserModel checkUser = userService.findUserById(user.getId());
        if (checkUser != null){
            Long id = userService.editUser(user,file,session);
            attributes.addFlashAttribute("message", "edit user successfully");
            return "redirect:/admin/user/edit?id=" + id;
        }else{
            attributes.addFlashAttribute("message", "Account does not exists");
            return "redirect:/admin/user/edit?id=" + user.getId();
        }
    }

    //-------------------xóa user--------------//
    @GetMapping("admin/user/delete")
    public String delete(@RequestParam Long id) {
        UserModel user = userService.findUserById(id);
        userService.deleteUser(user,id);
        return "redirect:/admin/user/list";
    }

    //---------------user role based----------------//
    @GetMapping("admin/user/authorization")
    public String authorization(Model model, @RequestParam Long id) {
        UserModel user = userService.findUserById(id);
        List<RoleModel> roles = (List<RoleModel>) userService.findRoleAll();
        model.addAttribute("uModel", user);
        model.addAttribute("rModel",roles);
        return "views/admin/user/authorization";
    }

    //chưa test
    @PostMapping("admin/user/doAuthorization")
    public String doAuthorization(@RequestParam Long id, @RequestParam(value = "roles") List<RoleModel> roles) {
        userService.authoUser(id, roles);
        return "redirect:/admin/user/authorization?id="+id;
    }

}
