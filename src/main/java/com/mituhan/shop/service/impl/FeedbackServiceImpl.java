package com.mituhan.shop.service.impl;

import com.mituhan.shop.JPAmysql.FeedbackJpa;
import com.mituhan.shop.model.FeedbackModel;
import com.mituhan.shop.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    FeedbackJpa feedbackJpa;

    @Override
    public Long getTotal() {
        return feedbackJpa.getTotalFeedback();
    }

    @Override
    public Page<FeedbackModel> findAllByIdIsLike(Optional<Long> Id, Pageable pageable) {
        return (Page<FeedbackModel>) feedbackJpa.findAllByIdIsLike(Id,pageable);
    }

    @Override
    public void saveFeedback(FeedbackModel feedback) {
        feedbackJpa.save(feedback);
    }

    @Override
    public void deleteFeedback(Long id) {
        feedbackJpa.deleteById(id);
    }
}
