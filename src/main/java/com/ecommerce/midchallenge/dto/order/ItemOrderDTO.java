package com.ecommerce.midchallenge.dto.order;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemOrderDTO {
    private String productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
}
