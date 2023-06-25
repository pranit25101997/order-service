package com.prnt.orderservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class OrderResponseDto {
    int articleNumber;
    BigDecimal subTotalCost;
    BigDecimal shipmentCost;
    BigDecimal totalCost;
}
