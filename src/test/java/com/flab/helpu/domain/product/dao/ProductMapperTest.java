package com.flab.helpu.domain.product.dao;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.flab.helpu.domain.product.domain.Product;
import com.flab.helpu.domain.product.exception.NoSuchProductException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@MybatisTest
public class ProductMapperTest {

  @Autowired
  private ProductMapper productMapper;

  @Test
  @DisplayName("상품 객체 insert 쿼리 테스트")
  void insertProduct() {
    Product product = Product.builder()
        .companyIdx(1L)
        .productName("테스트용")
        .productPrice(10000)
        .productMaker("테스트회사")
        .kcal(10000)
        .protein(120)
        .fat(110)
        .description("테스트상품설명")
        .carbohydrate(110)
        .createdBy("test")
        .build();

    productMapper.insertProduct(product);

    Product result = productMapper.findProductByProductName(product.getProductName())
        .orElseThrow(() -> {
          throw new NoSuchProductException("상품이 존재하지 않습니다.");
        });

    Assertions.assertEquals(product.getIdx(), result.getIdx());
    Assertions.assertEquals(product.getCompanyIdx(), result.getCompanyIdx());
    Assertions.assertEquals(product.getProductPrice(), result.getProductPrice());
    Assertions.assertEquals(product.getProductMaker(), result.getProductMaker());
    Assertions.assertEquals(product.getProductImgUrl(), result.getProductImgUrl());
    Assertions.assertEquals(product.getProductName(), result.getProductName());
    Assertions.assertEquals(product.getProtein(), result.getProtein());
    Assertions.assertEquals(product.getFat(), result.getFat());
    Assertions.assertEquals(product.getKcal(), result.getKcal());
    Assertions.assertEquals(product.getCarbohydrate(), result.getCarbohydrate());
    Assertions.assertEquals(product.getDescription(), result.getDescription());

  }

  @Test
  @DisplayName("필수 정보를 넣지 않았을때 insert 실패")
  void failInsertProduct() {
    //company idx 누락
    Product product = Product.builder()
        .idx(1L)
        .productName("테스트용")
        .productPrice(10000)
        .productMaker("테스트회사")
        .productImgUrl("www.test.com")
        .kcal(1000)
        .protein(10)
        .fat(10)
        .description("테스트상품설명")
        .carbohydrate(10)
        .createdBy("test")
        .updatedBy("test")
        .build();

    Assertions.assertThrows(DataIntegrityViolationException.class,
        () -> productMapper.insertProduct(product));

  }


  @Test
  @DisplayName("잘못된 company Idx로 Product 검색 시 검색 결과 없을때 Optional.empty 반환")
  void findNonExistentProductByCompanyIdx() {
    Long wrongCompanyIdx = 2L;

    Assertions.assertEquals(productMapper.findProductByCompanyIdx(wrongCompanyIdx),
        Optional.empty());

  }

  @Test
  @DisplayName("잘못된 product name으로 Product 검색 시 검색 결과 없을때 Optional.empty 반환")
  void findNonExistentProductByProductName() {
    String wrongNickname = "wrong";

    Assertions.assertEquals(productMapper.findProductByProductName(wrongNickname),
        Optional.empty());

  }
}