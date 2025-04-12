package com.moldavets.ecom_store.product.infrastructure.primary.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moldavets.ecom_store.product.infrastructure.primary.exception.MultipartPictureException;
import com.moldavets.ecom_store.product.infrastructure.primary.model.RestPicture;
import com.moldavets.ecom_store.product.infrastructure.primary.model.RestProduct;
import com.moldavets.ecom_store.product.model.Product;
import com.moldavets.ecom_store.product.service.ProductApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/api/products")
public class ProductsAdminController {

    private final ProductApplicationService productApplicationService;

    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public ProductsAdminController(ProductApplicationService productApplicationService) {
        this.productApplicationService = productApplicationService;
    }

    @PreAuthorize("hasAnyRole('" + ROLE_ADMIN + "')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RestProduct> save(MultipartHttpServletRequest request,
                                            @RequestPart("dto") String productRaw) throws JsonProcessingException {

        List<RestPicture> pictures = request.getFileMap()
                .values()
                .stream()
                .map(mapMultipartFileToRestPicture())
                .toList();

        RestProduct restProduct = objectMapper.readValue(productRaw, RestProduct.class);
        restProduct.addPictureAttachment(pictures);

        Product newProduct = RestProduct.to(restProduct);
        Product storedProduct = productApplicationService.createProduct(newProduct);
        return new ResponseEntity(RestProduct.from(storedProduct), HttpStatus.OK);
    }

    private Function<MultipartFile, RestPicture> mapMultipartFileToRestPicture() {
        return multipartFile -> {
            try {
                return new RestPicture(multipartFile.getBytes(), multipartFile.getContentType());
            } catch (IOException e) {
                throw new MultipartPictureException(
                        String.format("Failed to parse multipart file: %s", multipartFile.getOriginalFilename())
                );
            }
        };
    }


}
