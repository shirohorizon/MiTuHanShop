package com.mituhan.shop.model;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
@Data
@Table(name = "filtervalue")
public class FilterValueModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filtername_id", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    private FilterNameModel filterNames;

    @Column(name = "value")
    private String value;

    @Column(name = "price")
    private String price;

    @Column(name = "quantity")
    private Long quantity;
}
