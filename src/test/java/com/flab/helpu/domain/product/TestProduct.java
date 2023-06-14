package com.flab.helpu.domain.product;

import com.flab.helpu.common.config.ImageFile;
import com.flab.helpu.domain.product.domain.Product;

public class TestProduct {

  public static class TestProductExample {

    public static Product product1 = Product.builder()
        .idx(1L)
        .companyIdx(1L)
        .productName("테스트용")
        .productPrice(10000)
        .productMaker("테스트회사")
        .productImgUrl(ImageFile.IMAGE_NAME1)
        .kcal(1000)
        .protein(10)
        .fat(10)
        .description("테스트상품설명")
        .carbohydrate(10)
        .createdBy("test")
        .updatedBy("test")
        .build();

    public static Product product2 = Product.builder()
        .idx(2L)
        .companyIdx(2L)
        .productName("테스트용2")
        .productPrice(10000)
        .productMaker("테스트회사")
        .productImgUrl(ImageFile.IMAGE_NAME2)
        .kcal(1000)
        .protein(10)
        .fat(10)
        .description("테스트상품설명")
        .carbohydrate(10)
        .createdBy("test")
        .updatedBy("test")
        .build();
  }

}


