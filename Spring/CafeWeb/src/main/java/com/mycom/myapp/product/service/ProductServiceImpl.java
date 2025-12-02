package com.mycom.myapp.product.service;

import com.mycom.myapp.common.dto.PageRequest;
import com.mycom.myapp.common.entity.Product;
import com.mycom.myapp.common.entity.Stock;
import com.mycom.myapp.common.exception.BadRequestException;
import com.mycom.myapp.common.exception.NotFoundException;
import com.mycom.myapp.product.dao.ProductDao;
import com.mycom.myapp.product.dto.CreateProductRequest;
import com.mycom.myapp.product.dto.ProductListResponse;
import com.mycom.myapp.product.dto.ProductResponse;
import com.mycom.myapp.product.dto.UpdateProductRequest;
import com.mycom.myapp.stock.dao.StockDao;
import com.mycom.myapp.stock.dto.StockResponse;
import com.mycom.myapp.stock.dto.StockUpdateRequest;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductDao productDao;
  private final StockDao stockDao;

  public ProductServiceImpl(ProductDao productDao, StockDao stockDao) {
    this.productDao = productDao;
    this.stockDao = stockDao;
  }

  public ProductListResponse getProductList(PageRequest request) {
    if (request.getPage() < 1 || request.getPageSize() < 1) {
      throw new BadRequestException("page와 pageSize는 1 이상의 값이어야 합니다.");
    }

    List<ProductResponse> list = productDao.findAllProducts(request);
    int totalCount = productDao.countAll(request);
    int totalPages = (int) Math.ceil((double) totalCount / request.getPageSize());

    ProductListResponse response = new ProductListResponse();
    response.setList(list);
    response.setTotalCount(totalCount);
    response.setTotalPages(totalPages);
    response.setCurrentPage(request.getPage());
    return response;
  }

  public void deleteById(int id) {
    ProductResponse product = productDao.findById(id);
    if (product == null) {
      throw new NotFoundException("상품을 찾을 수 없습니다.");
    }

    StockResponse existStock = stockDao.findByProductId(id);
    if (existStock != null) {
      throw new IllegalStateException("해당 상품은 재고가 있어서 삭제할 수 없습니다.");
    }
    int deleted = productDao.deleteById(id);
    if (deleted <= 0) {
      throw new IllegalStateException("상품 삭제에 실패했습니다.");
    }
  }

  @Transactional
  public void updateProduct(UpdateProductRequest product) {
    if (product.getName() == null || product.getCategory() == null) {
      throw new BadRequestException("request에는 name, category를 포함하여야 합니다.");
    }

    ProductResponse exist = productDao.findById(product.getId());
    if (exist == null) {
      throw new NotFoundException("상품을 찾을 수 없습니다.");
    }

    Product updateProduct = Product.builder().
        id(product.getId()).
        name(product.getName()).
        category(product.getCategory()).
        price(product.getPrice()).
        stockManage(product.isStockManage()).build();

    if (product.isStockManage()) {
      StockResponse stock = stockDao.findByProductId(updateProduct.getId());
      if (stock == null) {
        Stock saveStock = Stock.builder().
            productId(updateProduct.getId()).build();
        stockDao.initStock(saveStock);
      }
    }

    int updated = productDao.updateProduct(updateProduct);
    if (updated <= 0) {
      throw new IllegalStateException("상품 수정에 실패했습니다.");
    }
  }

  @Transactional
  public int createProduct(CreateProductRequest product) {
    Product saveProduct = Product.builder().
        name(product.getName()).
        category(product.getCategory()).
        price(product.getPrice()).
        stockManage(product.isStockManage()).
        build();
    int inserted = productDao.registerProduct(saveProduct);
    if (inserted <= 0) {
      throw new IllegalStateException("상품 생성에 실패했습니다.");
    }

    if (product.isStockManage()) {
      int productId = saveProduct.getId();

      Stock saveStock = Stock.builder().
          productId(productId).build();
      stockDao.initStock(saveStock);
    }
    return saveProduct.getId();
  }

  public void updateStock(int id, StockUpdateRequest stock) {
    stock.setProductId(id);
    StockResponse existStock = stockDao.findByProductId(id);
    if (existStock == null) {
      throw new NotFoundException("재고 데이터를 찾을 수 없습니다. 상품의 재고 관리 여부 설정을 확인해주세요.");
    }
    int updated = stockDao.updateStockQuantity(stock);
    if (updated <= 0) {
      throw new IllegalStateException("상품 재고 추가에 실패했습니다.");
    }
  }
}
