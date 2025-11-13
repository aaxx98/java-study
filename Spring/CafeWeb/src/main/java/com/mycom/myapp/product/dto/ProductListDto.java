package com.mycom.myapp.product.dto;

import com.mycom.myapp.common.dto.ResponseListDto;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductListDto extends ResponseListDto {

  private List<ProductDto> list;
}
