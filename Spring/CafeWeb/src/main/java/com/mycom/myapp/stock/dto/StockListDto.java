package com.mycom.myapp.stock.dto;

import com.mycom.myapp.common.dto.ResponseListDto;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockListDto extends ResponseListDto {

  private List<StockDto> list;
}
