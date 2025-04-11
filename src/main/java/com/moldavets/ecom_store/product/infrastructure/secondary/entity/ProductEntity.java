package com.moldavets.ecom_store.product.infrastructure.secondary.entity;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productSequence")
    @SequenceGenerator(name = "productSequence", sequenceName = "product_sequence")
    @Column(name = "id")
    private Long id;

}
