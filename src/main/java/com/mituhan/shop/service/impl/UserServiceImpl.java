package com.mituhan.shop.service.impl;

import com.mituhan.shop.JPAmysql.UserJpa;
import com.mituhan.shop.helpers.Helpers;
import com.mituhan.shop.model.UserModel;
import com.mituhan.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserJpa userJpa;

    @Value("${config.upload_folder}")
    String UPLOADED_FOLDER;

    @Override
    public Long getTotal() {
        return userJpa.getTotalUser();
    }

    @Override
    public Page<UserModel> getUsers(Pageable pageable) {
        return userJpa.findAll(pageable);
    }

    @Override
    public UserModel findByUsername(String username) { return userJpa.findByUsername(username);}

    @Override
    public String saveUser(UserModel user, MultipartFile file, HttpSession session) {
        user.setPassword(Helpers.getMd5(user.getPassword()));
        user.setStatus(1);
        long millis=System.currentTimeMillis();
        Date datetime=new java.sql.Date(millis);
        user.setCreateddate(datetime);
        UserModel list = (UserModel) session.getAttribute("username");
        user.setCreatedby(list.getUsername());
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
            user.setImage(saveFolder.replace(UPLOADED_FOLDER, "")
                    + filename); //11_2020/tenfile.png
            userJpa.save(user);
            return "success";

        } catch (Exception e) {
            e.printStackTrace();
            return "error"+e.getMessage();

        }

    }

    @Override
    public UserModel getUserById(Long id) {
        return userJpa.findById(id).get();
    }


}
