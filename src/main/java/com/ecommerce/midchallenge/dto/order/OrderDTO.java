package com.ecommerce.midchallenge.dto.order;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private String id;
    private String status;
    private BigDecimal totalAmount;
    private List<ItemOrderDTO> items;
}
