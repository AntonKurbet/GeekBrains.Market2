package ru.geekbrains.market2.routing.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {

    private String productTitle;

    private int quantity;

    private int pricePerProduct;

    private BigDecimal price;

}
