package com.flab.helpu.domain.product.service;

import com.flab.helpu.domain.product.dao.ImageStorage;
import com.flab.helpu.domain.product.dao.ProductMapper;
import com.flab.helpu.domain.product.domain.Product;
import com.flab.helpu.domain.product.dto.CreateProductRequest;
import com.flab.helpu.domain.product.dto.CreateProductResponse;
import com.flab.helpu.domain.product.dto.UpdateProductRequest;
import com.flab.helpu.domain.product.exception.DuplicatedProductNameException;
import com.flab.helpu.domain.product.exception.NoSuchProductException;
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
//    if (imageUrl == null) imageUrl = "";

    Product insertRequestProduct = CreateProductRequest.toEntity(request, imageUrl);

    productMapper.insertProduct(insertRequestProduct);

    Product result = productMapper.findProductByProductName(request.getProductName())
        .orElseThrow(() -> {
          throw new NoSuchProductException("상품이 존재하지 않습니다.");
        });

    return CreateProductResponse.of(result);
  }

  @Transactional
  public void updateProduct(UpdateProductRequest request, MultipartFile multipartFile) {
    String imageUrl = imageStorage.store(multipartFile);
    Product product = UpdateProductRequest.toEntity(request, imageUrl);
    productMapper.updateProduct(product);
  }

  @Transactional
  public void deleteProduct(Long productIdx) {
    productMapper.deleteProduct(productIdx);
  }


  private void validateDuplicatedProductName(String productName) {
    productMapper.findProductByProductName(productName).ifPresent(product -> {
      throw new DuplicatedProductNameException("중복된 상품이름이 존재합니다.");
    });
  }
}
