package com.moldavets.ecom_store.product.model;

import com.moldavets.ecom_store.product.vo.ProductBrand;
import com.moldavets.ecom_store.product.vo.ProductName;
import com.moldavets.ecom_store.product.vo.ProductPrice;
import com.moldavets.ecom_store.product.vo.PublicId;
import lombok.Builder;

import java.util.Objects;

@Builder
public class ProductCart {

    private ProductName name;

    private ProductPrice price;

    private ProductBrand brand;

    private Picture picture;

    private PublicId publicId;

    public ProductCart(ProductName name, ProductPrice price, ProductBrand brand, Picture picture, PublicId publicId) {
        assertRequiredFields(name, price, brand, picture, publicId);
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.picture = picture;
        this.publicId = publicId;
    }

    public void assertRequiredFields(ProductName name,
                                     ProductPrice price,
                                     ProductBrand brand,
                                     Picture picture,
                                     PublicId publicId) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(price);
        Objects.requireNonNull(brand);
        Objects.requireNonNull(picture);
        Objects.requireNonNull(publicId);
    }


}
