package com.mycom.myapp.stock.dto;

import com.mycom.myapp.common.dto.PageResponse;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockListResponse extends PageResponse {

  private List<StockResponseWithProductName> list;
}
