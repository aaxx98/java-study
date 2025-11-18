package com.mycom.myapp.product.service;

import com.mycom.myapp.common.dto.PageRequestDto;
import com.mycom.myapp.common.exception.BadRequestException;
import com.mycom.myapp.common.exception.NotFoundException;
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
    if (request.getPage() < 1 || request.getPageSize() < 1) {
      throw new BadRequestException("page와 pageSize는 1 이상의 값이어야 합니다.");
    }

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

  public void deleteById(int id) {
    ProductDto product = productDao.findById(id);
    if (product == null) {
      throw new NotFoundException("상품을 찾을 수 없습니다.");
    }

    StockDto existStock = stockDao.findByProductId(id);
    if (existStock != null) {
      throw new IllegalStateException("해당 상품은 재고가 있어서 삭제할 수 없습니다.");
    }
    int deleted = productDao.deleteById(id);
    if (deleted <= 0) {
      throw new IllegalStateException("상품 삭제에 실패했습니다.");
    }
  }

  @Transactional
  public void updateProduct(int id, ProductDto product) {
    if (product.getName() == null || product.getCategory() == null) {
      throw new BadRequestException("request에는 name, category를 포함하여야 합니다.");
    }

    ProductDto exist = productDao.findById(id);
    if (exist == null) {
      throw new NotFoundException("상품을 찾을 수 없습니다.");
    }

    product.setId(id);

    if (product.isStockManage()) {
      StockDto stock = stockDao.findByProductId(id);
      if (stock == null) {
        stock = new StockDto();
        stock.setProductId(id);
        stockDao.initStock(stock);
      }
    }

    int updated = productDao.updateProduct(product);
    if (updated <= 0) {
      throw new IllegalStateException("상품 수정에 실패했습니다.");
    }
  }

  @Transactional
  public int createProduct(ProductDto product) {
    int inserted = productDao.registerProduct(product);
    if (inserted <= 0) {
      throw new IllegalStateException("상품 생성에 실패했습니다.");
    }

    if (product.isStockManage()) {
      int productId = product.getId();

      StockDto stock = new StockDto();
      stock.setProductId(productId);
      stockDao.initStock(stock);
    }
    return product.getId();
  }

  public void updateStock(int id, StockUpdateDto stock) {
    stock.setProductId(id);
    StockDto existStock = stockDao.findByProductId(id);
    if (existStock == null) {
      throw new NotFoundException("재고 데이터를 찾을 수 없습니다. 상품의 재고 관리 여부 설정을 확인해주세요.");
    }
    int updated = stockDao.updateStockQuantity(stock);
    if (updated <= 0) {
      throw new IllegalStateException("상품 재고 추가에 실패했습니다.");
    }
  }
}
