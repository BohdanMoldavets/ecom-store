package com.moldavets.ecom_store.product.infrastructure.secondary.repository;

import com.moldavets.ecom_store.order.model.order.vo.ProductPublicId;
import com.moldavets.ecom_store.product.infrastructure.secondary.entity.CategoryEntity;
import com.moldavets.ecom_store.product.infrastructure.secondary.entity.PictureEntity;
import com.moldavets.ecom_store.product.infrastructure.secondary.entity.ProductEntity;
import com.moldavets.ecom_store.product.model.FilterQuery;
import com.moldavets.ecom_store.product.model.Picture;
import com.moldavets.ecom_store.product.model.Product;
import com.moldavets.ecom_store.product.repository.ProductRepository;
import com.moldavets.ecom_store.product.vo.UserPublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public class SpringDataProductRepository implements ProductRepository {

    private final JpaCategoryRepository jpaCategoryRepository;

    private final JpaProductRepository jpaProductRepository;

    private final JpaProductPictureRepository jpaProductPictureRepository;

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
    public Optional<Product> findByPublicId(UserPublicId publicId) {
        return jpaProductRepository.findByPublicId(publicId.id()).map(ProductEntity::to);
    }

    @Override
    public Page<Product> findByCategoryExcludingOne(Pageable pageable, UserPublicId categoryPublicId, UserPublicId productPublicId) {
        return jpaProductRepository
                .findByCategoryPublicIdAndPublicIdNot(pageable, categoryPublicId.id(), productPublicId.id())
                .map(ProductEntity::to);
    }

    @Override
    public int delete(UserPublicId publicId) {
        return jpaProductRepository.deleteByPublicId(publicId.id());
    }

    @Override
    public void updateQuantity(ProductPublicId productPublicId, long quantity) {
        jpaProductRepository.updateQuantity(productPublicId.value(), quantity);
    }

    @Override
    public Page<Product> findAllFeaturedProduct(Pageable pageable) {
        return jpaProductRepository.findAllByFeaturedTrue(pageable).map(ProductEntity::to);
    }

    @Override
    public Page<Product> findByCategoryAndSize(Pageable pageable, FilterQuery filterQuery) {
        return jpaProductRepository
                .findByCategoryPublicIdAndSizesIn(pageable, filterQuery.id().id(), filterQuery.sizes())
                .map(ProductEntity::to);
    }

    @Override
    public List<Product> findByPublicIds(List<UserPublicId> publicIds) {
        List<UUID> uuids = publicIds.stream().map(UserPublicId::id).toList();
        return jpaProductRepository.findAllByPublicIdIn(uuids).stream()
                .map(ProductEntity::to).toList();
    }

    private void saveAllPictures(List<Picture> pictures, ProductEntity productEntity) {
        Set<PictureEntity> picturesEntities = PictureEntity.from(pictures);

        for (PictureEntity pictureEntity : picturesEntities) {
            pictureEntity.setProduct(productEntity);
        }

        jpaProductPictureRepository.saveAll(picturesEntities);
    }
}
