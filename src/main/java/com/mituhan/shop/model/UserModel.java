package com.mituhan.shop.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "user")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long Id;
    @Column(name = "Username")
    private String username;
    @Column(name = "Password")
    private String password;
    @Column(name = "Fullname")
    private String FullName;
    @Column(name = "Phone")
    private String phone;
    @Column(name = "Email")
    private String email;
    @Column(name = "Address")
    private String address;
    @Column(name = "status")
    private Integer status;
    @Column(name = "image")
    private String image;
    @Column(name = "createddate")
    private Date createddate;
    @Column(name = "modifieddate")
    private Date modifieddate;
    @Column(name = "createdby")
    private String createdby;
    @Column(name = "modifiedby")
    private String modifiedby;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "user_roles",
            joinColumns =  {@JoinColumn(name ="user_id")},
            inverseJoinColumns= {@JoinColumn(name="role_id")})
    private Set <RoleModel> roles = new HashSet<RoleModel>();
}
