package com.mituhan.shop.service;

import com.mituhan.shop.model.CategoryModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    public List<CategoryModel> findAll();
}
