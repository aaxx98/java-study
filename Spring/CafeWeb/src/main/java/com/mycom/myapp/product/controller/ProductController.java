package com.mycom.myapp.product.controller;

import com.mycom.myapp.common.dto.PageRequestDto;
import com.mycom.myapp.product.dto.ProductListDto;
import com.mycom.myapp.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
      return ResponseEntity
          .badRequest()
          .body("page와 pageSize는 1 이상의 값이어야 합니다.");
    }
    ProductListDto listDto = productService.getProductList(requestDto);

    return ResponseEntity.ok(listDto);
  }
}
