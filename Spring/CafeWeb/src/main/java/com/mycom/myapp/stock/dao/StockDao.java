package com.mycom.myapp.stock.dao;

import com.mycom.myapp.common.dto.PageRequest;
import com.mycom.myapp.common.entity.Stock;
import com.mycom.myapp.stock.dto.StockResponse;
import com.mycom.myapp.stock.dto.StockResponseWithProductName;
import com.mycom.myapp.stock.dto.StockUpdateRequest;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StockDao {

  List<StockResponseWithProductName> findAllStocks(PageRequest req);

  int countAll();

  StockResponse findByProductId(@Param("productId") int productId);

  int updateStockQuantity(StockUpdateRequest stock);

  void initStock(Stock stock);
}
