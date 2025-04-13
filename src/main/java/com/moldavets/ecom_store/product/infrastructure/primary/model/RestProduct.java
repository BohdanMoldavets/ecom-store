package com.moldavets.ecom_store.product.infrastructure.primary.model;

import com.moldavets.ecom_store.product.model.Product;
import com.moldavets.ecom_store.product.vo.*;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public class RestProduct {

    private String brand;
    private String color;
    private String description;
    private String name;
    private double price;
    private ProductSize size;
    private RestCategory category;
    private boolean featured;
    private List<RestPicture> pictures;
    private UUID publicId;
    private int nbInStock;

    public RestProduct() {
    }

    public RestProduct(String brand,
                       String color,
                       String description,
                       String name,
                       double price,
                       ProductSize size,
                       RestCategory category,
                       boolean featured,
                       List<RestPicture> pictures,
                       UUID publicId,
                       int nbInStock) {
        this.brand = brand;
        this.color = color;
        this.description = description;
        this.name = name;
        this.price = price;
        this.size = size;
        this.category = category;
        this.featured = featured;
        this.pictures = pictures;
        this.publicId = publicId;
        this.nbInStock = nbInStock;
    }

    public void addPictureAttachment(List<RestPicture> pictures) {
        this.pictures = pictures;
    }

    public static Product to(RestProduct product) {
       Product.ProductBuilder builder = Product.builder()
               .brand(new ProductBrand(product.getBrand()))
               .color(new ProductColor(product.getColor()))
               .description(new ProductDescription(product.getDescription()))
               .name(new ProductName(product.getName()))
               .price(new ProductPrice(product.getPrice()))
               .size(product.getSize())
               .category(RestCategory.to(product.getCategory()))
               .featured(product.isFeatured())
               .nbInStock(product.getNbInStock());

       if(product.getPublicId() != null) {
           builder.publicId(new PublicId(product.getPublicId()));
       }

       if(product.getPictures() != null && !product.getPictures().isEmpty()) {
           builder.pictures(RestPicture.to(product.getPictures()));
       }

       return builder.build();
    }

    public static RestProduct from(Product product) {
        return RestProduct.builder()
                .brand(product.getBrand().value())
                .color(product.getColor().value())
                .description(product.getDescription().value())
                .name(product.getName().value())
                .price(product.getPrice().value())
                .size(product.getSize())
                .category(RestCategory.from(product.getCategory()))
                .featured(product.isFeatured())
                .pictures(RestPicture.from(product.getPictures()))
                .publicId(product.getPublicId().id())
                .nbInStock(product.getNbInStock())
                .build();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ProductSize getSize() {
        return size;
    }

    public void setSize(ProductSize size) {
        this.size = size;
    }

    public RestCategory getCategory() {
        return category;
    }

    public void setCategory(RestCategory category) {
        this.category = category;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public List<RestPicture> getPictures() {
        return pictures;
    }

    public void setPictures(List<RestPicture> pictures) {
        this.pictures = pictures;
    }

    public UUID getPublicId() {
        return publicId;
    }

    public void setPublicId(UUID publicId) {
        this.publicId = publicId;
    }

    public int getNbInStock() {
        return nbInStock;
    }

    public void setNbInStock(int nbInStock) {
        this.nbInStock = nbInStock;
    }
}
