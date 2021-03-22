package com.mituhan.shop.service.impl;

import com.mituhan.shop.JPAmysql.CategoryJpa;
import com.mituhan.shop.model.CategoryModel;
import com.mituhan.shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryJpa categoryJpa;

    @Override
    public List<CategoryModel> findAll() {
        return categoryJpa.findAll();
    }
}
