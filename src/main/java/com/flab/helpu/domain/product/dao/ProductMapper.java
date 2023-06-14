package com.flab.helpu.domain.product.dao;

import com.flab.helpu.domain.product.domain.Product;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {

  void insertProduct(Product product);

  Optional<Product> findProductByIdx(Long idx);

  Optional<Product> findProductByCompanyIdx(Long companyIdx);

  Optional<Product> findProductByProductName(String productName);

  List<Product> findAllProduct();

  void deleteProduct(Long idx);

  void updateProduct(Product product);

}
