package com.flab.helpu.domain.product.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.flab.helpu.common.config.ImageFile;
import com.flab.helpu.domain.product.TestProduct.TestProductExample;
import com.flab.helpu.domain.product.dao.ImageStorage;
import com.flab.helpu.domain.product.dao.ProductMapper;
import com.flab.helpu.domain.product.domain.Product;
import com.flab.helpu.domain.product.dto.CreateProductRequest;
import com.flab.helpu.domain.product.dto.CreateProductResponse;
import com.flab.helpu.domain.product.dto.ProductResponse;
import com.flab.helpu.domain.product.dto.UpdateProductRequest;
import com.flab.helpu.domain.product.exception.DuplicatedProductNameException;
import com.flab.helpu.domain.product.exception.NoSuchProductException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

  @InjectMocks
  private ProductService productService;

  @Mock
  private ProductMapper productMapper;

  @Mock
  private ImageStorage imageStorage;

  @Test
  @DisplayName("상품 등록 성공.")
  void createProduct() {
    CreateProductRequest request = CreateProductRequest.builder()
        .companyIdx(TestProductExample.product1.getCompanyIdx())
        .productName(TestProductExample.product1.getProductName())
        .productPrice(TestProductExample.product1.getProductPrice())
        .productMaker(TestProductExample.product1.getProductMaker())
        .imageFile(ImageFile.MOCK_JPEG_FILE)
        .kcal(TestProductExample.product1.getKcal())
        .protein(TestProductExample.product1.getProtein())
        .fat(TestProductExample.product1.getFat())
        .description(TestProductExample.product1.getDescription())
        .carbohydrate(TestProductExample.product1.getCarbohydrate())
        .username(TestProductExample.product1.getCreatedBy())
        .build();

    doNothing().when(productMapper).insertProduct(any(Product.class));
    when(productMapper.findProductByProductName(any(String.class))).thenAnswer(new Answer() {
      private int count = 0;

      public Object answer(InvocationOnMock invocation) {
        if (++count == 1) {
          return Optional.empty();
        } else {
          return Optional.of(TestProductExample.product1);
        }
      }
    });

    CreateProductResponse result = productService.createProduct(request);

    Assertions.assertEquals(result.getIdx(), 1L);
    Assertions.assertEquals(result.getProductName(), "테스트용");
    verify(productMapper).insertProduct(any(Product.class));
    verify(imageStorage, times(1)).store(any());
  }

  @Test
  @DisplayName("상품 이름 중복")
  void duplicatedProductName() {
    CreateProductRequest request = CreateProductRequest.builder()
        .companyIdx(1L)
        .productName("테스트용")
        .productPrice(10000)
        .productMaker("테스트회사")
        .imageFile(ImageFile.MOCK_JPEG_FILE)
        .kcal(100)
        .protein(10)
        .fat(10)
        .description("테스트상품설명")
        .carbohydrate(10)
        .username("test")
        .build();

    when(productMapper.findProductByProductName(any(String.class))).thenReturn(
        Optional.of(TestProductExample.product1));

    Assertions.assertThrows(DuplicatedProductNameException.class,
        () -> productService.createProduct(request));
  }

  @Test
  @DisplayName("등록된 상품을 idx로 삭제한다.")
  void deleteProduct() {

    productService.deleteProduct(TestProductExample.product1.getIdx());

    verify(productMapper, times(1)).deleteProduct(anyLong());
  }

  @Test
  @DisplayName("이미지 포함 상품 정보 수정")
  void updateProductWithNewImage() {
    UpdateProductRequest request = UpdateProductRequest.builder()
        .companyIdx(TestProductExample.product2.getCompanyIdx())
        .productName(TestProductExample.product2.getProductName())
        .productPrice(TestProductExample.product2.getProductPrice())
        .productMaker(TestProductExample.product2.getProductMaker())
        .kcal(TestProductExample.product2.getKcal())
        .protein(TestProductExample.product2.getProtein())
        .fat(TestProductExample.product2.getFat())
        .description(TestProductExample.product2.getDescription())
        .carbohydrate(TestProductExample.product2.getCarbohydrate())
        .username(TestProductExample.product2.getCreatedBy())
        .build();

    Long productIdx = TestProductExample.product1.getIdx();

    when(imageStorage.store(ImageFile.MOCK_JPEG_FILE)).thenReturn("/" + ImageFile.IMAGE_NAME1);

    productService.updateProduct(request, ImageFile.MOCK_JPEG_FILE, productIdx);

    verify(productMapper, times(1)).updateProduct(any());
    verify(imageStorage, times(1)).store(any());
  }

  @Test
  @DisplayName("이미지 없이 상품 정보 수정")
  void updateProductWithoutNewImage() {
    UpdateProductRequest request = UpdateProductRequest.builder()
        .companyIdx(TestProductExample.product2.getCompanyIdx())
        .productName(TestProductExample.product2.getProductName())
        .productPrice(TestProductExample.product2.getProductPrice())
        .productMaker(TestProductExample.product2.getProductMaker())
        .kcal(TestProductExample.product2.getKcal())
        .protein(TestProductExample.product2.getProtein())
        .fat(TestProductExample.product2.getFat())
        .description(TestProductExample.product2.getDescription())
        .carbohydrate(TestProductExample.product2.getCarbohydrate())
        .username(TestProductExample.product2.getCreatedBy())
        .build();

    Long productIdx = TestProductExample.product1.getIdx();

    when(imageStorage.store(nullable(MultipartFile.class))).thenReturn("");

    productService.updateProduct(request, null, productIdx);

    verify(productMapper, times(1)).updateProduct(any());
    verify(imageStorage, times(1)).store(any());
  }

  @Test
  @DisplayName("상품 정보 가져오기 - imageURL 있을때")
  void getProduct() {
    Long productIdx = TestProductExample.product1.getIdx();

    when(productMapper.findProductByIdx(any(Long.class))).thenReturn(
        Optional.ofNullable(TestProductExample.product1));

    ProductResponse result = productService.getProduct(productIdx);

    Assertions.assertEquals(result.getIdx(), TestProductExample.product1.getIdx());
    Assertions.assertEquals(result.getProductName(), TestProductExample.product1.getProductName());

  }

  @Test
  @DisplayName("잘못된 상품 idx로 검색")
  void NoSuchProduct() {
    Long productIdx = 2L;

    when(productMapper.findProductByIdx(any(Long.class))).thenReturn(Optional.empty());

    Assertions.assertThrows(NoSuchProductException.class,
        () -> productService.getProduct(productIdx));

  }

}
