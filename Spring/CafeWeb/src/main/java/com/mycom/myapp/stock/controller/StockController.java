package com.mycom.myapp.stock.controller;

import com.mycom.myapp.common.dto.PageRequestDto;
import com.mycom.myapp.stock.dto.StockListDto;
import com.mycom.myapp.stock.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/api/stocks")
public class StockController {

  private final StockService stockService;


  public StockController(StockService stockService) {
    this.stockService = stockService;
  }

  @GetMapping
  public ResponseEntity<StockListDto> getStockList(@ModelAttribute PageRequestDto requestDto) {
    StockListDto listDto = stockService.getStockList(requestDto);
    return ResponseEntity.ok(listDto);
  }
}
