package com.mituhan.shop.model;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "filtername")
public class FilterNameModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    private ProductModel products;

    @OneToMany(mappedBy = "filterNames", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private Set<FilterValueModel> filterValueModels = new HashSet<FilterValueModel>();

    @Column(name = "name")
    private String name;
}
