package com.flab.helpu.domain.product.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.helpu.common.config.ImageFile;
import com.flab.helpu.domain.product.TestProduct.TestProductExample;
import com.flab.helpu.domain.product.controller.ProductController;
import com.flab.helpu.domain.product.dao.ImageStorage;
import com.flab.helpu.domain.product.dto.CreateProductRequest;
import com.flab.helpu.domain.product.dto.CreateProductRequestDto;
import com.flab.helpu.domain.product.dto.CreateProductResponse;
import com.flab.helpu.domain.product.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    mockMvc.perform(multipart("/product")
            .file(mockMultipartFileDto)
            .file(mockMultipartFileImage))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.productName").value(requestDto.getProductName()));
  }
}