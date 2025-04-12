package com.moldavets.ecom_store.product.infrastructure.secondary.repository;

import com.moldavets.ecom_store.product.infrastructure.secondary.entity.CategoryEntity;
import com.moldavets.ecom_store.product.infrastructure.secondary.entity.PictureEntity;
import com.moldavets.ecom_store.product.infrastructure.secondary.entity.ProductEntity;
import com.moldavets.ecom_store.product.model.Picture;
import com.moldavets.ecom_store.product.model.Product;
import com.moldavets.ecom_store.product.repository.ProductRepository;
import com.moldavets.ecom_store.product.vo.PublicId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class SpringDataProductRepository implements ProductRepository {

    private final JpaCategoryRepository jpaCategoryRepository;

    private final JpaProductRepository jpaProductRepository;

    private final JpaProductPictureRepository jpaProductPictureRepository;

    @Autowired
    public SpringDataProductRepository(JpaCategoryRepository jpaCategoryRepository,
                                       JpaProductRepository jpaProductRepository,
                                       JpaProductPictureRepository jpaProductPictureRepository) {
        this.jpaCategoryRepository = jpaCategoryRepository;
        this.jpaProductRepository = jpaProductRepository;
        this.jpaProductPictureRepository = jpaProductPictureRepository;
    }

    @Override
    public Product save(Product productToBeCreated) {
        ProductEntity productEntity = ProductEntity.from(productToBeCreated);
        CategoryEntity categoryEntity = jpaCategoryRepository.findByPublicId(productEntity.getCategory().getPublicId())
                .orElseThrow(() -> new RuntimeException("Entity not found"));
        //TODO EXCEPTION
        productEntity.setCategory(categoryEntity);

        ProductEntity storedProductEntity = jpaProductRepository.save(productEntity);

        saveAllPictures(productToBeCreated.getPictures(), storedProductEntity);

        return ProductEntity.to(storedProductEntity);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return jpaProductRepository.findAll(pageable).map(ProductEntity::to);
    }

    @Override
    public int delete(PublicId publicId) {
        return jpaProductRepository.deleteByPublicId(publicId.id());
    }

    private void saveAllPictures(List<Picture> pictures, ProductEntity productEntity) {
        Set<PictureEntity> picturesEntities = PictureEntity.from(pictures);

        for (PictureEntity pictureEntity : picturesEntities) {
            pictureEntity.setProduct(productEntity);
        }

        jpaProductPictureRepository.saveAll(picturesEntities);
    }
}
