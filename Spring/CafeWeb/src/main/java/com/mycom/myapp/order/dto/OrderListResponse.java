package com.mycom.myapp.order.dto;

import com.mycom.myapp.common.dto.PageResponse;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderListResponse extends PageResponse {

  private List<OrderResponse> list;
}
