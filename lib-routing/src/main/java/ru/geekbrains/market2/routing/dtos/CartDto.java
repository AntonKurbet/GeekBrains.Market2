package ru.geekbrains.market2.routing.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Data
public class CartDto {
    private List<CartItemDto> items;
    private BigDecimal totalPrice;
}
