package com.mycom.myapp.stock.service;

import com.mycom.myapp.common.dto.PageRequest;
import com.mycom.myapp.stock.dto.StockListResponse;

public interface StockService {

  public StockListResponse getStockList(PageRequest request);
}
