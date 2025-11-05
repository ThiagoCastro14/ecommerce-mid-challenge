package com.ecommerce.midchallenge.dto.order;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {
    private List<ItemOrderDTO> items;
}
