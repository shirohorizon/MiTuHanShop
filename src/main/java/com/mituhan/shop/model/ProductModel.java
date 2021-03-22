package com.mituhan.shop.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "product")
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "category_id", nullable = false, insertable = false, updatable = false)
    private CategoryModel category;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "products", cascade = {CascadeType.ALL})
    private Set<FilterNameModel> Products = new HashSet<FilterNameModel>();

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "material")
    private String material;

    @Column(name = "image")
    private String image;

    @Column(name = "status")
    private Integer status;

    @Column(name = "createddate")
    private Date createddate;
    @Column(name = "modifieddate")
    private Date modifieddate;
    @Column(name = "createdby")
    private String createdby;
    @Column(name = "modifiedby")
    private String modifiedby;
}
