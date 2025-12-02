package com.mycom.myapp.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderStateRequest {

  private int id;
  private String status;
}
