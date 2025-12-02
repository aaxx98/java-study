package com.mycom.myapp.product.controller;

import com.mycom.myapp.common.dto.PageRequest;
import com.mycom.myapp.product.dto.CreateProductRequest;
import com.mycom.myapp.product.dto.ProductListResponse;
import com.mycom.myapp.product.dto.UpdateProductRequest;
import com.mycom.myapp.product.service.ProductService;
import com.mycom.myapp.stock.dto.StockUpdateRequest;
import java.net.URI;
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
  public ResponseEntity<ProductListResponse> getProductList(
      @ModelAttribute PageRequest requestDto) {
    ProductListResponse listDto = productService.getProductList(requestDto);
    return ResponseEntity.ok(listDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
    productService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping
  public ResponseEntity<Void> saveProduct(@RequestBody CreateProductRequest product) {
    int createdId = productService.createProduct(product);
    URI location = URI.create("/api/products/" + createdId);
    return ResponseEntity.created(location).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> updateProduct(@PathVariable int id,
      @RequestBody UpdateProductRequest product) {
    product.setId(id);
    productService.updateProduct(product);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/stock")
  public ResponseEntity<Void> patchStockQuantity(
      @PathVariable int id, @RequestBody StockUpdateRequest stock) {
    productService.updateStock(id, stock);
    return ResponseEntity.noContent().build();
  }
}
