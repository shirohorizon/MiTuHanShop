package com.mituhan.shop.controller.admin;

import com.mituhan.shop.model.FeedbackModel;
import com.mituhan.shop.model.RoleModel;
import com.mituhan.shop.model.UserModel;
import com.mituhan.shop.service.FeedbackService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class FeedBackController {

    @Autowired
    FeedbackService feedbackService;


    //---------xem------------//
    @GetMapping("admin/feedback/list")
    public String list(Model model,
                       @RequestParam(defaultValue = "") Optional<Long> id,
                       @PageableDefault(size = 100) Pageable pageable){
        Page<FeedbackModel> feedback = feedbackService.findAllByIdIsLike(id,pageable);
        model.addAttribute("feedback", feedback);
        return "views/admin/feedback/index";
    }

    //---------xóa-----------//
    @GetMapping("admin/feedback/delete")
    public String delete(@RequestParam Long id) {
        feedbackService.deleteFeedback(id);
        return "redirect:/admin/feedback/list";
    }
}
