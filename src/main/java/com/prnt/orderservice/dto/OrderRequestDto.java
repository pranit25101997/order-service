package com.prnt.orderservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {
    int articleNumber;
    int orderNumber;
    int logisticCompany;
}
