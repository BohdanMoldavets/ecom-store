package com.moldavets.ecom_store.product.infrastructure.primary;

import com.moldavets.ecom_store.product.model.Picture;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record RestPicture(byte[] file,
                          String mimeType) {

    public RestPicture {
        if(file == null || file.length == 0) {
            throw new IllegalArgumentException("file is null or empty");
            //todo exception
        }

        if(mimeType == null || mimeType.isEmpty()) {
            throw new IllegalArgumentException("mimeType is null or empty");
            //todo exception
        }
    }

    public static Picture to(RestPicture picture) {
        return Picture.builder()
                .file(picture.file)
                .mimeType(picture.mimeType)
                .build();
    }

    public static List<Picture> to(List<RestPicture> pictures) {
        return pictures.stream().map(RestPicture::to).toList();
    }

    public static RestPicture from(Picture picture) {
        return RestPicture.builder()
                .file(picture.file())
                .mimeType(picture.mimeType())
                .build();
    }

    public static List<RestPicture> from(List<Picture> pictures) {
        return pictures.stream().map(RestPicture::from).toList();
    }
}
