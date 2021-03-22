package com.mituhan.shop.service;

import com.mituhan.shop.model.FeedbackModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface FeedbackService {
    public Long getTotal();
    public Page<FeedbackModel> findAllByIdIsLike(Optional<Long> username, Pageable pageable);
    public void saveFeedback(FeedbackModel feedback);
    public void deleteFeedback(Long id);
}
