package com.mituhan.shop.service.impl;

import com.mituhan.shop.JPAmysql.ProductJpa;
import com.mituhan.shop.model.CategoryModel;
import com.mituhan.shop.model.ProductModel;
import com.mituhan.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductJpa productJpa;

    @Value("${config.upload_folder}")
    String UPLOADED_FOLDER;

    @Override
    public Page<ProductModel> findAllByUsernameContaining(Optional<String> name, Pageable pageable) {
        return (Page<ProductModel>) productJpa.findAllByNameContaining(name,pageable);
    }

    @Override
    public Page<ProductModel> findAllByCategoryContaining(CategoryModel category, Pageable pageable) {
        return (Page<ProductModel>) productJpa.findAllByCategoryContaining(category,pageable);
    }

    @Override
    public ProductModel findProductById(Long id) {
        return productJpa.findById(id).get();
    }

    @Override
    public void deleteProduct(ProductModel product, Long id) {
        deleteFileImage(product);
        productJpa.deleteById(id);
    }

    public ProductModel saveFileImage(ProductModel product, MultipartFile file){
        try {
            Date date = new Date();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int year = localDate.getYear();
            int month = localDate.getMonthValue();

            String saveFolder = UPLOADED_FOLDER + month + "_" + year + "/";

            ///Users/mac/Documents/uploads/11_2020
            File dir = new File(saveFolder);
            if (dir.isFile() || !dir.exists()) {
                dir.mkdir(); //tạo mới một folder Users/mac/Documents/uploads/11_2020
            }

            String filename = System.currentTimeMillis() + file.getOriginalFilename();

            byte[] bytes = file.getBytes();
            Path path = Paths.get(dir.getAbsolutePath() + "/" + filename);
            Files.write(path, bytes);
            product.setImage(saveFolder.replace(UPLOADED_FOLDER, "")
                    + filename); //11_2020/tenfile.png
            return product;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteFileImage(ProductModel product){
        try {
            File file = new File(UPLOADED_FOLDER + product.getImage());
            file.delete();
            product.getFilterNames().forEach(f->{
                f.getFilterValues().forEach(f1->{
                    File file1 = new File(UPLOADED_FOLDER + f1.getImage());
                    file1.delete();
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
