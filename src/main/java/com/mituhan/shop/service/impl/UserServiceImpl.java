package com.mituhan.shop.service.impl;

import com.mituhan.shop.JPAmysql.RoleJpa;
import com.mituhan.shop.JPAmysql.UserJpa;
import com.mituhan.shop.helpers.Helpers;
import com.mituhan.shop.model.RoleModel;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserJpa userJpa;

    @Autowired
    private RoleJpa roleJpa;

    @Value("${config.upload_folder}")
    String UPLOADED_FOLDER;

    @Override
    public Long getTotal() {
        return userJpa.getTotalUser();
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
        //test add
        user.setCreatedby("admin");
        UserModel userModel = saveFileImage(user,file);
        if(userModel != null){
            userJpa.save(userModel);
            return "success";
        }else {
            return "error";
        }
    }

    @Override
    public Long editUser(UserModel user, MultipartFile file, HttpSession session) {
        user.setPassword(Helpers.getMd5(user.getPassword()));
        user.setStatus(1);
        long millis=System.currentTimeMillis();
        Date datetime=new java.sql.Date(millis);
        user.setModifieddate(datetime);
        UserModel list = (UserModel) session.getAttribute("username");
        //test add
        user.setModifiedby("admin");
        UserModel roles = userJpa.findUserJoinRoleByUsername(user.getUsername());
        if(roles != null){
            user.setRoles(roles.getRoles());
        }
        deleteFileImage(user);
        UserModel userModel = saveFileImage(user,file);
        if(userModel != null){
            userJpa.save(userModel);
            UserModel userUserRole = userJpa.findByUsername(userModel.getUsername());
            return userUserRole.getId();
        }else {
            return null;
        }
    }

    @Override
    public UserModel findUserById(Long id) {
        return userJpa.findById(id).get();
    }

    @Override
    public void deleteUser(UserModel user, Long id) {
        deleteFileImage(user);
        userJpa.deleteById(id);
    }

    @Override
    public List<RoleModel> findRoleAll() {
        return roleJpa.findAll();
    }

    @Override
    public void authoUser(Long id, List<RoleModel> roles) {
        UserModel user = findUserById(id);
        roles.forEach(r->{
            user.setRoles((Set<RoleModel>) r);
        });
        userJpa.save(user);
    }

    @Override
    public Page<UserModel> findAllByUsernameContaining(Optional<String> username, Pageable pageable) {
        return (Page<UserModel>) userJpa.findAllByUsernameContaining(username,pageable);
    }


    public UserModel saveFileImage(UserModel user, MultipartFile file){
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
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteFileImage(UserModel user){
        try {
            File file = new File(UPLOADED_FOLDER + user.getImage());
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
