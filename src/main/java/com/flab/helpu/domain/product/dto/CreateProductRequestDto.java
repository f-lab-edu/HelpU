package com.flab.helpu.domain.product.dto;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class CreateProductRequestDto {

  @NotNull(message = "공급사의 Idx값이 없습니다.")
  private Long companyIdx;

  @NotNull(message = "상품의 이름이 없습니다.")
  private String productName;

  @NotNull(message = "상품의 가격이 없습니다.")
  private int productPrice;

  @NotNull(message = "상품 제조사의 이름이 없습니다.")
  private String productMaker;

  @NotNull(message = "상품의 설명이 없습니다.")
  private String description;

  @NotNull(message = "상품의 단백질 정보가 없습니다.")
  private int protein;

  @NotNull(message = "상품의 탄수화물 정보가 없습니다.")
  private int carbohydrate;

  @NotNull(message = "상품의 지방 정보가 없습니다.")
  private int fat;

  @NotNull(message = "상품의 칼로리 정보가 없습니다.")
  private int kcal;

  @NotNull(message = "유저의 정보가 없습니다.")
  private String username;


  public static CreateProductRequest of(CreateProductRequestDto product,
      MultipartFile multipartFile) {
    return CreateProductRequest.builder()
        .companyIdx(product.getCompanyIdx())
        .imageFile(multipartFile)
        .productName(product.getProductName())
        .productMaker(product.getProductMaker())
        .productPrice(product.getProductPrice())
        .fat(product.getFat())
        .kcal(product.getKcal())
        .carbohydrate(product.getCarbohydrate())
        .description(product.getDescription())
        .protein(product.getProtein())
        .username(product.getUsername())
        .build();
  }

}


