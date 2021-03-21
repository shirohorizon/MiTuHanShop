package com.mituhan.shop.model;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "feedback")
public class FeedbackModel {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long Id;

    @Column(name = "Name")
    private String name;
    @Column(name = "Phone")
    private String phone;
    @Column(name = "Email")
    private String email;
    @Column(name = "Address")
    private String address;
    @Column(name = "content")
    private String content;
    @Column(name = "status")
    private Integer status;
    @Column(name = "createddate")
    private Date createddate;
}
