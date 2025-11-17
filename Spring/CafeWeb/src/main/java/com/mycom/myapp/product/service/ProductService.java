package com.mycom.myapp.product.service;

import com.mycom.myapp.common.dto.PageRequestDto;
import com.mycom.myapp.product.dao.ProductDao;
import com.mycom.myapp.product.dto.ProductDto;
import com.mycom.myapp.product.dto.ProductListDto;
import com.mycom.myapp.stock.dao.StockDao;
import com.mycom.myapp.stock.dto.StockDto;
import com.mycom.myapp.stock.dto.StockUpdateDto;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

  private final ProductDao productDao;
  private final StockDao stockDao;

  public ProductService(ProductDao productDao, StockDao stockDao) {
    this.productDao = productDao;
    this.stockDao = stockDao;
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
      StockDto existStock = stockDao.findByProductId(id);
      if (existStock != null) {
        throw new IllegalStateException("해당 상품은 재고가 있어서 삭제할 수 없습니다.");
      }
      productDao.deleteById(id);
      return true;
    }
    return false;
  }

  @Transactional
  public boolean updateProduct(int id, ProductDto product) {
    product.setId(id);

    if (product.isStockManage()) {
      int productId = product.getId();
      StockDto existStock = stockDao.findByProductId(productId);
      if (existStock == null) {
        StockDto stock = new StockDto();
        stock.setProductId(productId);
        stockDao.initStock(stock);
      }
    }
    return productDao.updateProduct(product) > 0;
  }

  @Transactional
  public boolean createProduct(ProductDto product) {
    productDao.registerProduct(product);

    if (product.isStockManage()) {
      int productId = product.getId();
      StockDto existStock = stockDao.findByProductId(productId);
      if (existStock == null) {
        StockDto stock = new StockDto();
        stock.setProductId(productId);
        stockDao.initStock(stock);
      }
    }
    return true;
  }

  public boolean updateStock(int id, StockUpdateDto stock) {
    stock.setProductId(id);
    StockDto existStock = stockDao.findByProductId(id);
    if (existStock == null) {
      return false;
    }

    stockDao.updateStockQuantity(stock);
    return true;
  }
}
