package com.moldavets.ecom_store.product.service;

import com.moldavets.ecom_store.product.model.Category;
import com.moldavets.ecom_store.product.model.Product;
import com.moldavets.ecom_store.product.repository.CategoryRepository;
import com.moldavets.ecom_store.product.repository.ProductRepository;
import com.moldavets.ecom_store.product.vo.PublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductApplicationService {

    private final ProductCRUD productCRUD;
    private final CategoryCRUD categoryCRUD;

    public ProductApplicationService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productCRUD = new ProductCRUD(productRepository);
        this.categoryCRUD = new CategoryCRUD(categoryRepository);
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
    public PublicId deleteProduct(PublicId productId) {
        return productCRUD.deleteById(productId);
    }

    @Transactional
    public Category createCategory(Category newCategory) {
        return categoryCRUD.save(newCategory);
    }

    @Transactional
    public PublicId deleteCategory(PublicId productId) {
        return categoryCRUD.deleteById(productId);
    }
}
