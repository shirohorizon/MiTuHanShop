package com.mituhan.shop.JPAmysql;

import com.mituhan.shop.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpa extends CrudRepository<ProductModel, Long>,
        PagingAndSortingRepository<ProductModel,Long>, JpaRepository<ProductModel, Long> {
}
