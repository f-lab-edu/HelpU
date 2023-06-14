package com.flab.helpu.domain.product.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

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

}
