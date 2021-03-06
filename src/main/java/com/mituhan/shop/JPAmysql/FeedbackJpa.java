package com.mituhan.shop.JPAmysql;

import com.mituhan.shop.model.FeedbackModel;
import com.mituhan.shop.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedbackJpa extends
        CrudRepository<FeedbackModel, Long>,
        PagingAndSortingRepository<FeedbackModel,Long>, JpaRepository<FeedbackModel, Long> {

    @Query("SELECT COUNT(u) FROM FeedbackModel u")
    public long getTotalFeedback();

    Page<FeedbackModel> findAllByIdIsLike(Optional<Long> Id, Pageable pageable);

}
