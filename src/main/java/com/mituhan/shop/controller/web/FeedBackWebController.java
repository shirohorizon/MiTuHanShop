package com.mituhan.shop.controller.web;

import com.mituhan.shop.model.FeedbackModel;
import com.mituhan.shop.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FeedBackWebController {

    @Autowired
    FeedbackService feedbackService;

    //---------thêm----------//
    @GetMapping("web")
    public void add(Model model) {
        model.addAttribute("feedback", new FeedbackModel());
    }

    @PostMapping("web/feedback")
    public String doadd(FeedbackModel feedbackModel, RedirectAttributes attributes){
        feedbackService.saveFeedback(feedbackModel);
        attributes.addFlashAttribute("message", "Done");
        return "redirect:/web";
    }
}
