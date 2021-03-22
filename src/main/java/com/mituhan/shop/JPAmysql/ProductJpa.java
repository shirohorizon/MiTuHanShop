package com.mituhan.shop.JPAmysql;

import com.mituhan.shop.model.CategoryModel;
import com.mituhan.shop.model.ProductModel;
import com.mituhan.shop.model.RoleModel;
import com.mituhan.shop.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductJpa extends CrudRepository<ProductModel, Long>,
        PagingAndSortingRepository<ProductModel,Long>, JpaRepository<ProductModel, Long> {

    Page<ProductModel> findAllByNameContaining(Optional<String> name, Pageable pageable);

    Page<ProductModel> findAllByCategoryContaining(CategoryModel username, Pageable pageable);
}
