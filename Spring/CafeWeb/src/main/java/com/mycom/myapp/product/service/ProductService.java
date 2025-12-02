package com.mycom.myapp.product.service;

import com.mycom.myapp.common.dto.PageRequest;
import com.mycom.myapp.product.dto.CreateProductRequest;
import com.mycom.myapp.product.dto.ProductListResponse;
import com.mycom.myapp.product.dto.UpdateProductRequest;
import com.mycom.myapp.stock.dto.StockUpdateRequest;

public interface ProductService {

  public ProductListResponse getProductList(PageRequest request);

  public void deleteById(int id);

  public void updateProduct(UpdateProductRequest product);

  public int createProduct(CreateProductRequest product);

  public void updateStock(int id, StockUpdateRequest stock);
}
