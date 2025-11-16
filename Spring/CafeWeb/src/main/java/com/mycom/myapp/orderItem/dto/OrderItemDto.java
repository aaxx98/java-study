package com.mycom.myapp.orderItem.dto;

import com.mycom.myapp.product.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderItemDto {

  private int id;
  private int orderId;
  private int productId;
  private int quantity;
  private int price;

  private ProductDto product;
}
