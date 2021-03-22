package com.mituhan.shop.model;

import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "category")
public class CategoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;


    @Column(name="parent_id")
    private Long parent_id;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(cascade = {CascadeType.DETACH})
    @JoinColumn(name = "parent_id",
            insertable = false, updatable = false)
    private CategoryModel parent;

    @OneToOne(mappedBy = "parent", cascade = {CascadeType.DETACH})
    private CategoryModel child;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = {CascadeType.ALL})
    private Set<ProductModel> Products = new HashSet<ProductModel>();

    @Column(name = "createddate")
    private Date createdDate;
    @Column(name = "modifieddate")
    private Date modifiedDate;
    @Column(name = "createdby")
    private String createdBy;
    @Column(name = "modifiedby")
    private String modifiedBy;
}
