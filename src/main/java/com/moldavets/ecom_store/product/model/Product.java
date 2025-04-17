package com.moldavets.ecom_store.product.model;

import com.moldavets.ecom_store.product.vo.*;
import lombok.Builder;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Builder
public class Product {

    private final ProductBrand brand;

    private final ProductColor color;

    private final ProductDescription description;

    private final ProductName name;

    private final ProductPrice price;

    private final ProductSize size;

    private final Category category;

    private final List<Picture> pictures;

    private Long dbId;

    private boolean featured;

    private UserPublicId publicId;

    private Integer nbInStock;

    public Product(ProductBrand brand,
                   ProductColor color,
                   ProductDescription description,
                   ProductName name,
                   ProductPrice price,
                   ProductSize size,
                   Category category,
                   List<Picture> pictures,
                   Long dbId,
                   boolean featured,
                   UserPublicId publicId,
                   Integer nbInStock) {
        assertRequiredFields(brand, color, description, name, price, size, category, pictures, featured, nbInStock);
        this.brand = brand;
        this.color = color;
        this.description = description;
        this.name = name;
        this.price = price;
        this.size = size;
        this.category = category;
        this.pictures = pictures;
        this.dbId = dbId;
        this.featured = featured;
        this.publicId = publicId;
        this.nbInStock = nbInStock;
    }

    public void initDefaultFields() {
        this.publicId = new UserPublicId(UUID.randomUUID());
    }

    private void assertRequiredFields(ProductBrand brand,
                   ProductColor color,
                   ProductDescription description,
                   ProductName name,
                   ProductPrice price,
                   ProductSize size,
                   Category category,
                   List<Picture> pictures,
                   boolean featured,
                   Integer nbInStock) {
        Objects.requireNonNull(color);
        Objects.requireNonNull(description);
        Objects.requireNonNull(name);
        Objects.requireNonNull(price);
        Objects.requireNonNull(size);
        Objects.requireNonNull(category);
        Objects.requireNonNull(pictures);
        Objects.requireNonNull(featured);
        Objects.requireNonNull(nbInStock);
    }

    public ProductBrand getBrand() {
        return brand;
    }

    public ProductColor getColor() {
        return color;
    }

    public ProductDescription getDescription() {
        return description;
    }

    public ProductName getName() {
        return name;
    }

    public ProductPrice getPrice() {
        return price;
    }

    public ProductSize getSize() {
        return size;
    }

    public Category getCategory() {
        return category;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public Long getDbId() {
        return dbId;
    }

    public boolean isFeatured() {
        return featured;
    }

    public UserPublicId getPublicId() {
        return publicId;
    }

    public Integer getNbInStock() {
        return nbInStock;
    }
}
