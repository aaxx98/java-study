package com.mycom.myapp.stock.dao;

import com.mycom.myapp.common.dto.PageRequestDto;
import com.mycom.myapp.stock.dto.StockDto;
import com.mycom.myapp.stock.dto.StockUpdateDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StockDao {

  List<StockDto> findAllStocks(PageRequestDto req);

  int countAll();

  StockDto findByProductId(@Param("productId") int productId);

  int updateStockQuantity(StockUpdateDto stock);

  void initStock(StockDto stock);
}
