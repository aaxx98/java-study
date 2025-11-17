package com.mycom.myapp.product.dao;

import com.mycom.myapp.common.dto.PageRequestDto;
import com.mycom.myapp.product.dto.ProductDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductDao {

  ProductDto findById(@Param("id") int id);

  List<ProductDto> findAllProducts(PageRequestDto req);

  int countAll(PageRequestDto req);

  void deleteById(@Param("id") int id);

  void registerProduct(ProductDto product);

  int updateProduct(ProductDto product);
}
