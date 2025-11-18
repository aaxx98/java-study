package com.mycom.myapp.stock.service;

import com.mycom.myapp.common.dto.PageRequestDto;
import com.mycom.myapp.stock.dao.StockDao;
import com.mycom.myapp.stock.dto.StockDto;
import com.mycom.myapp.stock.dto.StockListDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StockService {

  private final StockDao stockDao;

  public StockService(StockDao stockDao) {
    this.stockDao = stockDao;
  }

  public StockListDto getStockList(PageRequestDto request) {
    if (request.getPage() < 1 || request.getPageSize() < 1) {
      throw new IllegalArgumentException("page와 pageSize는 1 이상의 값이어야 합니다.");
    }

    List<StockDto> list = stockDao.findAllStocks(request);
    int totalCount = stockDao.countAll();
    int totalPages = (int) Math.ceil((double) totalCount / request.getPageSize());

    StockListDto response = new StockListDto();
    response.setList(list);
    response.setTotalCount(totalCount);
    response.setTotalPages(totalPages);
    response.setCurrentPage(request.getPage());
    return response;
  }
}
