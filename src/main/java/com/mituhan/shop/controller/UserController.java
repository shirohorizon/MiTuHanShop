package com.mituhan.shop.controller;

import com.mituhan.shop.JPAmysql.UserJpa;
import com.mituhan.shop.model.RoleModel;
import com.mituhan.shop.model.UserModel;
import com.mituhan.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    //lấy danh sách user theo limit
    @GetMapping("admin/user/list")
    public String list(Model model,
                       @RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "15") int offset) {
        Page<UserModel> users = userService.getUsers(PageRequest.of(page - 1, offset));

        model.addAttribute("users", users.getContent());

        Long totalRecord = userService.getTotal();
        Double totalPage = Math.ceil((double)totalRecord / offset);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("offset", offset);
        model.addAttribute("currentPage", page);
        return "views/admin/user/index";
    }


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
            }else if (rMessage.indexOf("error") != 0){
                attributes.addFlashAttribute("error", "Add failed " + rMessage);
            }
            return "redirect:/admin/user/add";
        }else {
            attributes.addFlashAttribute("message", "user already exists");
            return "redirect:/admin/user/add";
        }
    }


    @GetMapping("admin/user/edit")
    public String edit(Model model, @RequestParam Long id) {
        UserModel user = userService.getUserById(id);
        model.addAttribute("gModel", user);
        return "views/admin/user/edit";
    }

//    @PostMapping("admin/user/doedit")
//    public String doEdit(UserModel gModel, @RequestParam Long id,
//                         RedirectAttributes attributes,
//                         @RequestParam(name = "file") MultipartFile file, HttpSession session) {
//        UserModel user = (UserModel) gModel;
//        UserModel list = (UserModel) session.getAttribute("username");
//        list = userService.findByUsernameAndPasswordJoinRole(user.getUsername(), user.getPassword(), user.getStatus());
//        if (list != null){
//            user.setRole_name(list.getRole_name());
//            user.setRolevalues(list.getRolevalues());
//        }
//        userService.saveUser(user,file,session);
//        attributes.addFlashAttribute("message", "edit user successfully");
//        return "redirect:/admin/user/edit?id=" + id;
//    }


}
