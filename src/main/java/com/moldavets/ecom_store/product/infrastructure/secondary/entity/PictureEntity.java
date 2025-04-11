package com.moldavets.ecom_store.product.infrastructure.secondary.entity;

import com.moldavets.ecom_store.common.jpa.AbstractAuditingEntity;
import com.moldavets.ecom_store.product.model.Picture;
import jakarta.persistence.*;
import lombok.Builder;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "product_picture")
@Builder
public class PictureEntity extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pictureSequence")
    @SequenceGenerator(name = "pictureSequence", sequenceName = "product_picture_sequence")
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "file", nullable = false)
    private byte[] file;

    @Column(name = "file_content_type", nullable = false)
    private String mimeType;

    public PictureEntity() {
    }

    public PictureEntity(Long id, byte[] file, String mimeType) {
        this.id = id;
        this.file = file;
        this.mimeType = mimeType;
    }

    public static PictureEntity from(Picture picture) {
        return PictureEntity.builder()
                .file(picture.file())
                .mimeType(picture.mimeType())
                .build();
    }

    public static List<PictureEntity> from(List<Picture> pictures) {
        return pictures.stream().map(PictureEntity::from).collect(Collectors.toList());
    }

    public static Picture to (PictureEntity pictureEntity) {
        return Picture.builder()
                .file(pictureEntity.getFile())
                .mimeType(pictureEntity.getMimeType())
                .build();
    }

    public static List<Picture> to (List<PictureEntity> pictureEntity) {
        return pictureEntity.stream().map(PictureEntity::to).collect(Collectors.toList());
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        PictureEntity that = (PictureEntity) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
