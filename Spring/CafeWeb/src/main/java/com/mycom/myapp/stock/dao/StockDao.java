package com.mycom.myapp.stock.dao;

import com.mycom.myapp.common.dto.PageRequestDto;
import com.mycom.myapp.stock.dto.StockDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StockDao {

  List<StockDto> findAllStocks(PageRequestDto req);

  int countAll();
}
