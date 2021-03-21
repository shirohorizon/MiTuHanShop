package com.mituhan.shop.JPAmysql;

import com.mituhan.shop.model.FeedbackModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackJpa extends
        CrudRepository<FeedbackModel, Long>,
        PagingAndSortingRepository<FeedbackModel,Long>, JpaRepository<FeedbackModel, Long> {

    @Query("SELECT COUNT(u) FROM FeedbackModel u")
    public long getTotalUser();

    Page<FeedbackModel> findAll(Pageable pageable);

}
