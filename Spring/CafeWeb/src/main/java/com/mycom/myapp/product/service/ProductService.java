package com.mycom.myapp.product.service;

import com.mycom.myapp.common.dto.PageRequestDto;
import com.mycom.myapp.product.dao.ProductDao;
import com.mycom.myapp.product.dto.ProductDto;
import com.mycom.myapp.product.dto.ProductListDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  private final ProductDao productDao;

  public ProductService(ProductDao productDao) {
    this.productDao = productDao;
  }

  public ProductListDto getProductList(PageRequestDto request) {
    List<ProductDto> list = productDao.findAllProducts(request);
    int totalCount = productDao.countAll(request);
    int totalPages = (int) Math.ceil((double) totalCount / request.getPageSize());

    ProductListDto response = new ProductListDto();
    response.setList(list);
    response.setTotalCount(totalCount);
    response.setTotalPages(totalPages);
    response.setCurrentPage(request.getPage());
    return response;

  }

  public boolean deleteById(int id) {
    ProductDto product = productDao.findById(id);
    if (product != null) {
      productDao.deleteById(id);
      return true;
    }
    return false;
  }
}
