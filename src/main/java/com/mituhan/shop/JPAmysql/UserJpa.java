package com.mituhan.shop.JPAmysql;

import com.mituhan.shop.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpa extends
        CrudRepository<UserModel, Long>,
        PagingAndSortingRepository<UserModel,Long>, JpaRepository<UserModel, Long> {

    @Query("SELECT COUNT(u) FROM UserModel u")
    public long getTotalUser();

    Page<UserModel> findAll(Pageable pageable);

    @Query("SELECT e FROM UserModel e WHERE e.username = :username")
    UserModel findByUsername(@Param("username") String username);
}
