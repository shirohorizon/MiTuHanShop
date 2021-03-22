package com.mituhan.shop.service.impl;

import com.mituhan.shop.JPAmysql.CategoryJpa;
import com.mituhan.shop.model.CategoryModel;
import com.mituhan.shop.model.UserModel;
import com.mituhan.shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryJpa categoryJpa;

    @Override
    public List<CategoryModel> findAll() {
        return categoryJpa.findAll();
    }

    @Override
    public Page<CategoryModel> findAllByNameContaining(Optional<String> name, Pageable pageable) {
        return (Page<CategoryModel>) categoryJpa.findAllByNameContaining(name,pageable);
    }

    @Override
    public String saveCategory(CategoryModel category, HttpSession session) {
        long millis=System.currentTimeMillis();
        Date datetime=new java.sql.Date(millis);
        category.setCreatedDate(datetime);
        UserModel list = (UserModel) session.getAttribute("username");
        //test add
        category.setCreatedBy("admin");
        categoryJpa.save(category);
        return "success";
    }

    @Override
    public CategoryModel findByName(String name) { return categoryJpa.findByName(name);}

    @Override
    public CategoryModel findCategoryById(Long id) {
        return categoryJpa.findById(id).get();
    }

    @Override
    public Long editCategory(CategoryModel category, HttpSession session) {
        long millis=System.currentTimeMillis();
        Date datetime=new java.sql.Date(millis);
        category.setModifiedDate(datetime);
        UserModel list = (UserModel) session.getAttribute("username");
        //test add
        category.setModifiedBy("admin");
        categoryJpa.save(category);
        return category.getId();
    }

    @Override
    public void deleteCategory(Long id) {
        categoryJpa.deleteById(id);
    }
}
