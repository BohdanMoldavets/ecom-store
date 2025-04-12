package com.moldavets.ecom_store.product.infrastructure.secondary.entity;

import com.moldavets.ecom_store.common.jpa.AbstractAuditingEntity;
import com.moldavets.ecom_store.product.model.Product;
import com.moldavets.ecom_store.product.vo.*;
import jakarta.persistence.*;
import lombok.Builder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "product")
@Builder
public class ProductEntity extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productSequence")
    @SequenceGenerator(name = "productSequence", sequenceName = "product_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "color")
    private String color;

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "featured")
    private boolean featured;

    @Enumerated(EnumType.STRING)
    @Column(name = "size")
    private ProductSize size;

    @Column(name = "public_id", unique = true)
    private UUID publicId;

    @Column(name = "nb_in_stock")
    private Integer nbInStock;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private Set<PictureEntity> pictures = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "category_fk", referencedColumnName = "id")
    private CategoryEntity category;

    public ProductEntity() {
    }

    public ProductEntity(Long id,
                         String brand,
                         String color,
                         String description,
                         String name,
                         double price,
                         boolean featured,
                         ProductSize size,
                         UUID publicID,
                         Integer nbInStock,
                         Set<PictureEntity> pictures,
                         CategoryEntity category) {
        this.id = id;
        this.brand = brand;
        this.color = color;
        this.description = description;
        this.name = name;
        this.price = price;
        this.featured = featured;
        this.size = size;
        this.publicId = publicID;
        this.nbInStock = nbInStock;
        this.pictures = pictures;
        this.category = category;
    }

    public static ProductEntity from(Product product) {
        ProductEntityBuilder builder = ProductEntity.builder();

        if(product.getDbId() != null) {
            builder.id(product.getDbId());
        }

        return builder
                .brand(product.getBrand().value())
                .color(product.getColor().value())
                .description(product.getDescription().value())
                .name(product.getName().value())
                .price(product.getPrice().value())
                .featured(product.isFeatured())
                .size(product.getSize())
                .nbInStock(product.getNbInStock())
                .publicId(product.getPublicId().id())
                .category(CategoryEntity.from(product.getCategory()))
                .pictures(PictureEntity.from(product.getPictures()))
                .build();
    }

    public static Product to(ProductEntity entity) {
        return Product.builder()
                .brand(new ProductBrand(entity.getBrand()))
                .color(new ProductColor(entity.getColor()))
                .description(new ProductDescription(entity.getDescription()))
                .name(new ProductName(entity.getName()))
                .price(new ProductPrice(entity.getPrice()))
                .size(entity.getSize())
                .publicId(new PublicId(entity.getPublicId()))
                .category(CategoryEntity.to(entity.getCategory()))
                .pictures(PictureEntity.to(entity.getPictures()))
                .featured(entity.isFeatured())
                .nbInStock(entity.getNbInStock())
                .build();
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public ProductSize getSize() {
        return size;
    }

    public void setSize(ProductSize size) {
        this.size = size;
    }

    public UUID getPublicId() {
        return publicId;
    }

    public void setPublicId(UUID publicID) {
        this.publicId = publicID;
    }

    public Integer getNbInStock() {
        return nbInStock;
    }

    public void setNbInStock(Integer nbInStock) {
        this.nbInStock = nbInStock;
    }

    public Set<PictureEntity> getPictures() {
        return pictures;
    }

    public void setPictures(Set<PictureEntity> pictures) {
        this.pictures = pictures;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        ProductEntity that = (ProductEntity) object;
        return Double.compare(price, that.price) == 0 && featured == that.featured && Objects.equals(brand, that.brand) && Objects.equals(color, that.color) && Objects.equals(description, that.description) && Objects.equals(name, that.name) && size == that.size && Objects.equals(publicId, that.publicId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, color, description, name, price, featured, size, publicId);
    }
}
