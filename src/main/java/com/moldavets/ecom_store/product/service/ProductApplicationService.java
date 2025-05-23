package com.moldavets.ecom_store.product.service;

import com.moldavets.ecom_store.order.model.order.model.OrderProductQuantity;
import com.moldavets.ecom_store.product.model.Category;
import com.moldavets.ecom_store.product.model.FilterQuery;
import com.moldavets.ecom_store.product.model.Product;
import com.moldavets.ecom_store.product.repository.CategoryRepository;
import com.moldavets.ecom_store.product.repository.ProductRepository;
import com.moldavets.ecom_store.product.vo.UserPublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductApplicationService {

    private final ProductCRUD productCRUD;
    private final CategoryCRUD categoryCRUD;
    private final ProductShop productShop;
    private final ProductUpdater productUpdater;

    public ProductApplicationService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productCRUD = new ProductCRUD(productRepository);
        this.categoryCRUD = new CategoryCRUD(categoryRepository);
        this.productShop = new ProductShop(productRepository);
        this.productUpdater = new ProductUpdater(productRepository);
    }

    @Transactional
    public Product createProduct(Product newProduct) {
        return productCRUD.save(newProduct);
    }

    @Transactional(readOnly = true)
    public Page<Product> findAllProducts(Pageable pageable) {
        return productCRUD.findAll(pageable);
    }

    @Transactional
    public UserPublicId deleteProduct(UserPublicId productId) {
        return productCRUD.deleteById(productId);
    }

    @Transactional
    public Category createCategory(Category newCategory) {
        return categoryCRUD.save(newCategory);
    }

    @Transactional
    public UserPublicId deleteCategory(UserPublicId productId) {
        return categoryCRUD.deleteById(productId);
    }

    @Transactional(readOnly = true)
    public Page<Category> findAllCategories(Pageable pageable) {
        return categoryCRUD.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Product> getFeaturedProducts(Pageable pageable) {
        return productShop.getFeaturedProducts(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Product> findProductById(UserPublicId productId) {
        return productCRUD.findByPublicId(productId);
    }

    @Transactional(readOnly = true)
    public Page<Product> findRelatedProducts(Pageable pageable, UserPublicId productId) {
        return productShop.findRelated(pageable, productId);
    }

    @Transactional(readOnly = true)
    public Page<Product> filter(Pageable pageable, FilterQuery filterQuery) {
        return productShop.filter(pageable, filterQuery);
    }

    @Transactional(readOnly = true)
    public List<Product> getProductsByPublicIdsIn(List<UserPublicId> publicIds) {
        return productCRUD.findAllByPublicIdIn(publicIds);
    }

    @Transactional
    public void updateProductQuantity(List<OrderProductQuantity> orderProductQuantities) {
        productUpdater.updateProductQuantity(orderProductQuantities);
    }
}
