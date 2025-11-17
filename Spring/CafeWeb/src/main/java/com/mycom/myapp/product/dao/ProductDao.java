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

  int deleteById(@Param("id") int id); // delete된 행 수 반환

  int registerProduct(ProductDto product); // insert된 행 수 반환

  int updateProduct(ProductDto product); // update된 행 수 반환
}
