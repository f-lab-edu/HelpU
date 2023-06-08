package com.flab.helpu.domain.product.service;

import com.flab.helpu.domain.product.dao.ImageStorage;
import com.flab.helpu.domain.product.dao.ProductMapper;
import com.flab.helpu.domain.product.domain.Product;
import com.flab.helpu.domain.product.dto.CreateProductRequest;
import com.flab.helpu.domain.product.dto.CreateProductResponse;
import com.flab.helpu.domain.product.dto.ProductResponse;
import com.flab.helpu.domain.product.dto.UpdateProductRequest;
import com.flab.helpu.domain.product.exception.DuplicatedProductNameException;
import com.flab.helpu.domain.product.exception.NoSuchProductException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductMapper productMapper;
  private final ImageStorage imageStorage;

  @Transactional
  public CreateProductResponse createProduct(CreateProductRequest request) {

    validateDuplicatedProductName(request.getProductName());

    String imageUrl = imageStorage.store(request.getImageFile());

    Product insertRequestProduct = CreateProductRequest.toEntity(request, imageUrl);

    productMapper.insertProduct(insertRequestProduct);

    Product result = productMapper.findProductByProductName(request.getProductName())
        .orElseThrow(() -> {
          throw new NoSuchProductException("상품이 존재하지 않습니다.");
        });

    return CreateProductResponse.of(result);
  }

  @Transactional
  public void updateProduct(UpdateProductRequest request, MultipartFile multipartFile,
      Long productIdx) {
    String imageUrl = imageStorage.store(multipartFile);
    Product product = UpdateProductRequest.toEntity(request, imageUrl, productIdx);
    productMapper.updateProduct(product);
  }

  @Transactional
  public void deleteProduct(Long productIdx) {
    productMapper.deleteProduct(productIdx);
  }

  public ProductResponse getProduct(Long productIdx) {
    Product product = productMapper.findProductByIdx(productIdx).orElseThrow(() -> {
      throw new NoSuchProductException("상품이 존재하지 않습니다.");
    });
    String imageUrl = product.getProductImgUrl();

    if (Objects.equals(product.getProductImgUrl(), "")) {
      imageUrl = "90b19dcd-7100-47f5-8516-dddab09617c5.png";
    }

    return ProductResponse.of(product, imageUrl);

  }

  private void validateDuplicatedProductName(String productName) {
    productMapper.findProductByProductName(productName).ifPresent(product -> {
      throw new DuplicatedProductNameException("중복된 상품이름이 존재합니다.");
    });
  }
}
