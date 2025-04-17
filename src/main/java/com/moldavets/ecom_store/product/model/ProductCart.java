package com.moldavets.ecom_store.product.model;

import com.moldavets.ecom_store.product.vo.ProductBrand;
import com.moldavets.ecom_store.product.vo.ProductName;
import com.moldavets.ecom_store.product.vo.ProductPrice;
import com.moldavets.ecom_store.product.vo.UserPublicId;
import lombok.Builder;

import java.util.Objects;

@Builder
public class ProductCart {

    private ProductName name;

    private ProductPrice price;

    private ProductBrand brand;

    private Picture picture;

    private UserPublicId publicId;

    public ProductCart(ProductName name, ProductPrice price, ProductBrand brand, Picture picture, UserPublicId publicId) {
        assertRequiredFields(name, price, brand, picture, publicId);
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.picture = picture;
        this.publicId = publicId;
    }

    public static ProductCart from (Product product) {
        return ProductCart.builder()
                .name(product.getName())
                .price(product.getPrice())
                .brand(product.getBrand())
                .picture(product.getPictures().stream().findFirst().orElseThrow())
                .publicId(product.getPublicId())
                .build();
    }

    private void assertRequiredFields(ProductName name,
                                     ProductPrice price,
                                     ProductBrand brand,
                                     Picture picture,
                                     UserPublicId publicId) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(price);
        Objects.requireNonNull(brand);
        Objects.requireNonNull(picture);
        Objects.requireNonNull(publicId);
    }

    public ProductName getName() {
        return name;
    }

    public void setName(ProductName name) {
        this.name = name;
    }

    public ProductPrice getPrice() {
        return price;
    }

    public void setPrice(ProductPrice price) {
        this.price = price;
    }

    public ProductBrand getBrand() {
        return brand;
    }

    public void setBrand(ProductBrand brand) {
        this.brand = brand;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public UserPublicId getPublicId() {
        return publicId;
    }

    public void setPublicId(UserPublicId publicId) {
        this.publicId = publicId;
    }
}
