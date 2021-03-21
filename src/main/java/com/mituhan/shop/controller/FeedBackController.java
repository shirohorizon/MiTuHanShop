package com.mituhan.shop.controller;

import com.mituhan.shop.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FeedBackController {



    @GetMapping("admin/feedback/list")
    public String list(Model model,
                       @RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "100") int offset){
        return "views/admin/user/index";
    }
}
