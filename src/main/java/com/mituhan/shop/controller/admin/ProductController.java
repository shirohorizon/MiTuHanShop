package com.mituhan.shop.controller.admin;

import com.mituhan.shop.model.CategoryModel;
import com.mituhan.shop.model.ProductModel;
import com.mituhan.shop.model.RoleModel;
import com.mituhan.shop.model.UserModel;
import com.mituhan.shop.service.CategoryService;
import com.mituhan.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    //----------lấy tất cả product theo keyword và phân trang----------//
    @GetMapping("admin/product/list")
    public String list(Model model,@RequestParam(defaultValue = "") Optional<String> keyword,
                       @RequestParam(defaultValue = "") CategoryModel category,
                       @PageableDefault(size = 100) Pageable pageable){
        Page<ProductModel> productModels;
        if (category == null){
            productModels = productService.findAllByUsernameContaining(keyword, pageable);
            model.addAttribute("products", productModels);
        }else {
            productModels = productService.findAllByCategoryContaining(category, pageable);
            model.addAttribute("products", productModels);
        }
        List<CategoryModel> categorys = (List<CategoryModel>) categoryService.findAll();
        model.addAttribute("categorys", categorys);
        return "views/admin/product/index";
    }

    //---------------thêm product---------------------//
    
    //---------------Sửa product----------------------//

    //---------------xóa product----------------------//
    @GetMapping("admin/product/delete")
    public String delete(@RequestParam Long id) {
        ProductModel product = productService.findProductById(id);
        productService.deleteProduct(product,id);
        return "redirect:/admin/user/list";
    }

}
