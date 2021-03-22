package com.mituhan.shop.service;

import com.mituhan.shop.model.CategoryModel;
import com.mituhan.shop.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
public interface CategoryService {
    public List<CategoryModel> findAll();
    public Page<CategoryModel> findAllByNameContaining(Optional<String> name, Pageable pageable);
    public String saveCategory(CategoryModel category, HttpSession session);
    public CategoryModel findByName(String name);
    public CategoryModel findCategoryById(Long id);
    public Long editCategory(CategoryModel category, HttpSession session);
    public void deleteCategory(Long id);
}
