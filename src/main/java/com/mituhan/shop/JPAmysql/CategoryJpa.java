package com.mituhan.shop.JPAmysql;

import com.mituhan.shop.model.CategoryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryJpa extends CrudRepository<CategoryModel, Long>,
        PagingAndSortingRepository<CategoryModel,Long>, JpaRepository<CategoryModel, Long> {

    Page<CategoryModel> findAllByNameContaining(Optional<String> name,Pageable pageable);

    @Query("SELECT e FROM CategoryModel e WHERE e.name = :name")
    CategoryModel findByName(@Param("name") String name);
}
