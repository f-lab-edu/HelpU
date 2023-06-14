package com.flab.helpu.domain.product.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.helpu.domain.product.TestProduct.TestProductExample;
import com.flab.helpu.domain.product.controller.ProductController;
import com.flab.helpu.domain.product.dao.ImageStorage;
import com.flab.helpu.domain.product.dto.CreateProductRequest;
import com.flab.helpu.domain.product.dto.CreateProductRequestDto;
import com.flab.helpu.domain.product.dto.CreateProductResponse;
import com.flab.helpu.domain.product.dto.ProductResponse;
import com.flab.helpu.domain.product.dto.UpdateProductRequest;
import com.flab.helpu.domain.product.service.ProductService;
import com.flab.helpu.domain.user.exception.DuplicatedValueException;
import com.flab.helpu.domain.user.exception.NoSuchUserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
@WebAppConfiguration
public class ProductControllerTest {

  @InjectMocks
  ProductController productController;

  @MockBean
  ProductService productService;

  @MockBean
  private ImageStorage imageStorage;

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private MockMvc mockMvc;

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @DisplayName("상품 등록 성공")
  void successCreateProduct() throws Exception {
    CreateProductRequestDto requestDto = CreateProductRequestDto.builder()
        .companyIdx(TestProductExample.product1.getCompanyIdx())
        .productName(TestProductExample.product1.getProductName())
        .productPrice(TestProductExample.product1.getProductPrice())
        .productMaker(TestProductExample.product1.getProductMaker())
        .kcal(TestProductExample.product1.getKcal())
        .protein(TestProductExample.product1.getProtein())
        .fat(TestProductExample.product1.getFat())
        .description(TestProductExample.product1.getDescription())
        .carbohydrate(TestProductExample.product1.getCarbohydrate())
        .username(TestProductExample.product1.getCreatedBy())
        .build();

    MockMultipartFile mockMultipartFileImage = new MockMultipartFile(
        "image",
        "image",
        MediaType.IMAGE_JPEG_VALUE,
        new byte[10]
    );

    MockMultipartFile mockMultipartFileDto = new MockMultipartFile(
        "request",
        "request",
        "application/json",
        objectMapper.writeValueAsBytes(requestDto)
    );

    CreateProductResponse response = CreateProductResponse.of(TestProductExample.product1);

    when(productService.createProduct(any(CreateProductRequest.class))).thenReturn(response);

    mockMvc.perform(multipart(HttpMethod.POST, "/product")
            .file(mockMultipartFileDto)
            .file(mockMultipartFileImage))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.productName").value(requestDto.getProductName()));
  }

  @Test
  @DisplayName("상품 등록 실패 - 상품이름중복")
  void duplicatedProductName() throws Exception {
    CreateProductRequestDto requestDto = CreateProductRequestDto.builder()
        .companyIdx(TestProductExample.product1.getCompanyIdx())
        .productName(TestProductExample.product1.getProductName())
        .productPrice(TestProductExample.product1.getProductPrice())
        .productMaker(TestProductExample.product1.getProductMaker())
        .kcal(TestProductExample.product1.getKcal())
        .protein(TestProductExample.product1.getProtein())
        .fat(TestProductExample.product1.getFat())
        .description(TestProductExample.product1.getDescription())
        .carbohydrate(TestProductExample.product1.getCarbohydrate())
        .username(TestProductExample.product1.getCreatedBy())
        .build();

    MockMultipartFile mockMultipartFileImage = new MockMultipartFile(
        "image",
        "image",
        MediaType.IMAGE_JPEG_VALUE,
        new byte[10]
    );

    MockMultipartFile mockMultipartFileDto = new MockMultipartFile(
        "request",
        "request",
        "application/json",
        objectMapper.writeValueAsBytes(requestDto)
    );

    when(productService.createProduct(any(CreateProductRequest.class))).thenThrow(
        DuplicatedValueException.class);

    mockMvc.perform(multipart("/product")
            .file(mockMultipartFileDto)
            .file(mockMultipartFileImage))
        .andDo(print())
        .andExpect(status().isBadRequest());

    verify(productService, times(1))
        .createProduct(any(CreateProductRequest.class));
  }

  @Test
  @DisplayName("상품 정보 업데이트")
  void updateProduct() throws Exception {
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

    MockMultipartFile mockMultipartFileImage = new MockMultipartFile(
        "image",
        "image",
        MediaType.IMAGE_PNG_VALUE,
        new byte[10]
    );

    MockMultipartFile mockMultipartFileDto = new MockMultipartFile(
        "request",
        "request",
        "application/json",
        objectMapper.writeValueAsBytes(request)
    );

    mockMvc.perform(multipart(HttpMethod.PATCH, "/product/{productIdx}", productIdx)
            .file(mockMultipartFileDto)
            .file(mockMultipartFileImage))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();
  }

  @Test
  @DisplayName("상품 삭제")
  void deleteProduct() throws Exception {

    Long productIdx = TestProductExample.product1.getIdx();

    mockMvc.perform(delete("/product/{productIdx}", productIdx))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();
  }

  @Test
  @DisplayName("상품 정보 가져오기")
  void getProduct() throws Exception {

    Long productIdx = TestProductExample.product1.getIdx();

    ProductResponse response = ProductResponse.of(TestProductExample.product1,
        TestProductExample.product1.getProductImgUrl());

    when(productService.getProduct(any(Long.class))).thenReturn(response);

    mockMvc.perform(get("/product/{productIdx}", productIdx))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productName").value(TestProductExample.product1.getProductName()));
  }

  @Test
  @DisplayName("잘못된 상품 idx로 상품 정보 가져오기")
  void NoSuchProduct() throws Exception {

    Long productIdx = 3L;

    when(productService.getProduct(any(Long.class))).thenThrow(NoSuchUserException.class);

    mockMvc.perform(get("/product/{productIdx}", productIdx))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }


}