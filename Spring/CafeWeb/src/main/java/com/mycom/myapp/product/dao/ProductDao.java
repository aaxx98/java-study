package com.mycom.myapp.product.dao;

import com.mycom.myapp.common.dto.PageRequest;
import com.mycom.myapp.common.entity.Product;
import com.mycom.myapp.product.dto.ProductResponse;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductDao {

  ProductResponse findById(@Param("id") int id);

  List<ProductResponse> findAllProducts(PageRequest req);

  int countAll(PageRequest req);

  int deleteById(@Param("id") int id); // delete된 행 수 반환

  int registerProduct(Product product); // insert된 행 수 반환

  int updateProduct(Product product); // update된 행 수 반환
}
