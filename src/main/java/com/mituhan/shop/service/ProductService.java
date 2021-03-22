package com.mituhan.shop.service;

import com.mituhan.shop.model.CategoryModel;
import com.mituhan.shop.model.ProductModel;
import com.mituhan.shop.model.RoleModel;
import com.mituhan.shop.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ProductService {
    public Page<ProductModel> findAllByUsernameContaining(Optional<String> name, Pageable pageable);
    public Page<ProductModel> findAllByCategoryContaining(CategoryModel category, Pageable pageable);
    public ProductModel findProductById(Long id);
    public void deleteProduct(ProductModel product, Long id);
}
