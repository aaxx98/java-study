package com.mycom.myapp.product.dao;

import com.mycom.myapp.common.dto.PageRequestDto;
import com.mycom.myapp.product.dto.ProductDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDao {

  List<ProductDto> findAllProducts(PageRequestDto req);

  int countAll();
}
