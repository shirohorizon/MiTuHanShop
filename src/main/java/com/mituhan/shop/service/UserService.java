package com.mituhan.shop.service;

import com.mituhan.shop.model.RoleModel;
import com.mituhan.shop.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    public Long getTotal();

    public UserModel findByUsername(String username);
    public String saveUser(UserModel user, MultipartFile file, HttpSession session);
    public UserModel findUserById(Long id);
    public Long editUser(UserModel user, MultipartFile file, HttpSession session);
    public void deleteUser(UserModel user, Long id);
    public List<RoleModel> findRoleAll();
    public void authoUser(Long id, List<RoleModel> roles);
    public Page<UserModel> findAllByUsernameContaining(Optional<String> username, Pageable pageable);

}
