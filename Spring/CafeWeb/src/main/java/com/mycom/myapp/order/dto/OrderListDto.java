package com.mycom.myapp.order.dto;

import com.mycom.myapp.common.dto.ResponseListDto;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderListDto extends ResponseListDto {

  private List<OrderDto> list;
}
