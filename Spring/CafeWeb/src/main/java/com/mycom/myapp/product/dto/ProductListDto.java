package com.mycom.myapp.product.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductListDto {

  private List<ProductDto> list;
  private int totalCount;
  private int totalPages;
  private int currentPage;
}
