package com.mituhan.shop.JPAmysql;

import com.mituhan.shop.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryJpa extends CrudRepository<CategoryModel, Long>,
        PagingAndSortingRepository<CategoryModel,Long>, JpaRepository<CategoryModel, Long> {
}
