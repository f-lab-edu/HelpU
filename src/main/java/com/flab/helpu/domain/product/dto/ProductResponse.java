package com.flab.helpu.domain.product.dto;

import com.flab.helpu.domain.product.domain.Product;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {
  private Long idx;
  private Long companyIdx;
  private String productName;
  private int productPrice;
  private String productMaker;
  private String productImgUrl;
  private String description;
  private int protein;
  private int carbohydrate;
  private int fat;
  private int kcal;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;
  private LocalDateTime deletedAt;

  public static ProductResponse of(Product product, String imageurl) {
    return ProductResponse.builder()
        .idx(product.getIdx())
        .companyIdx(product.getCompanyIdx())
        .productImgUrl(imageurl)
        .productName(product.getProductName())
        .productMaker(product.getProductMaker())
        .productPrice(product.getProductPrice())
        .fat(product.getFat())
        .kcal(product.getKcal())
        .carbohydrate(product.getCarbohydrate())
        .description(product.getDescription())
        .protein(product.getProtein())
        .updatedAt(LocalDateTime.now())
        .createdAt(LocalDateTime.now())
        .updatedBy(product.getUpdatedBy())
        .createdBy(product.getCreatedBy())
        .deletedAt(product.getDeletedAt())
        .build();
  }

}

