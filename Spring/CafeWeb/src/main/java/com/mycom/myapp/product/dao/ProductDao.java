package com.mycom.myapp.product.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDao {

  List<ProductDao> findAllProducts();
}
