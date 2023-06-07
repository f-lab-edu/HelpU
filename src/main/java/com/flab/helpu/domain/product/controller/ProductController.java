package com.flab.helpu.domain.product.controller;

import com.flab.helpu.domain.product.dto.CreateProductRequest;
import com.flab.helpu.domain.product.dto.CreateProductRequestDto;
import com.flab.helpu.domain.product.dto.CreateProductResponse;
import com.flab.helpu.domain.product.dto.UpdateProductRequest;
import com.flab.helpu.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping
  public ResponseEntity<CreateProductResponse> createProduct(
      @RequestPart(value = "request") CreateProductRequestDto request,
      @RequestPart(value = "image") MultipartFile multipartFile) {

    CreateProductRequest productRequest = CreateProductRequestDto.of(request, multipartFile);

    CreateProductResponse product = productService.createProduct(productRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(product);

  }

  @PatchMapping
  public ResponseEntity<Void> updateProduct(
      @Validated @RequestPart UpdateProductRequest request,
      @RequestPart MultipartFile multipartFile) {

    productService.updateProduct(request, multipartFile);

    return ResponseEntity.status(HttpStatus.OK).build();

  }

  @DeleteMapping
  public ResponseEntity<Void> deleteProduct(@PathVariable Long productIdx) {

    productService.deleteProduct(productIdx);

    return ResponseEntity.status(HttpStatus.OK).build();
  }

}
