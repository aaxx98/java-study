package com.mycom.myapp.stock.service;

import com.mycom.myapp.common.dto.PageRequest;
import com.mycom.myapp.stock.dao.StockDao;
import com.mycom.myapp.stock.dto.StockListResponse;
import com.mycom.myapp.stock.dto.StockResponseWithProductName;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {

  private final StockDao stockDao;

  public StockServiceImpl(StockDao stockDao) {
    this.stockDao = stockDao;
  }

  public StockListResponse getStockList(PageRequest request) {
    if (request.getPage() < 1 || request.getPageSize() < 1) {
      throw new IllegalArgumentException("page와 pageSize는 1 이상의 값이어야 합니다.");
    }

    List<StockResponseWithProductName> list = stockDao.findAllStocks(request);
    int totalCount = stockDao.countAll();
    int totalPages = (int) Math.ceil((double) totalCount / request.getPageSize());

    StockListResponse response = new StockListResponse();
    response.setList(list);
    response.setTotalCount(totalCount);
    response.setTotalPages(totalPages);
    response.setCurrentPage(request.getPage());
    return response;
  }
}
