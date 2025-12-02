package com.mycom.myapp.product.dto;

import com.mycom.myapp.common.dto.PageResponse;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductListResponse extends PageResponse {

  private List<ProductResponse> list;
}
