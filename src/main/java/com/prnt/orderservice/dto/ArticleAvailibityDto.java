package com.prnt.orderservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ArticleAvailibityDto {
    int articleNumber;
    String name;
    BigDecimal price;
    int inStock;
    boolean isAvailable;
}
