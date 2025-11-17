package com.mycom.myapp.product.controller;

import com.mycom.myapp.common.dto.PageRequestDto;
import com.mycom.myapp.product.dto.ProductDto;
import com.mycom.myapp.product.dto.ProductListDto;
import com.mycom.myapp.product.service.ProductService;
import com.mycom.myapp.stock.dto.StockUpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/api/products")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public ResponseEntity<?> getProductList(@ModelAttribute PageRequestDto requestDto) {
    if (requestDto.getPage() < 1 || requestDto.getPageSize() < 1) {
      throw new IllegalArgumentException("page와 pageSize는 1 이상의 값이어야 합니다.");
//      return ResponseEntity
//          .badRequest()
//          .body("page와 pageSize는 1 이상의 값이어야 합니다.");
    }
    ProductListDto listDto = productService.getProductList(requestDto);

    return ResponseEntity.ok(listDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
    boolean deleted = productService.deleteById(id);
    if (!deleted) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().build();
  }

  @PostMapping
  public ResponseEntity<Void> saveProduct(@RequestBody ProductDto product) {
    boolean created = productService.createProduct(product);
    if (!created) {
      throw new IllegalArgumentException("상품 생성 중 에러 발생");
    }
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestBody ProductDto product) {
    boolean updated = productService.updateProduct(id, product);
    if (!updated) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/{id}/stock")
  public ResponseEntity<?> patchStockQuantity(
      @PathVariable int id, @RequestBody StockUpdateDto stock) {
    boolean updated = productService.updateStock(id, stock);
    if (!updated) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().build();
  }
}
