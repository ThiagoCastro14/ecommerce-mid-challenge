package com.ecommerce.midchallenge.mapper.order;

import com.ecommerce.midchallenge.domain.order.*;
import com.ecommerce.midchallenge.dto.order.*;
import org.mapstruct.Mapper;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    default OrderDTO toDto(Order order) {
        if (order == null) return null;

        List<ItemOrderDTO> itemDTOs = order.getItems() != null
                ? order.getItems().stream().map(this::toItemDto).collect(Collectors.toList())
                : null;

        return OrderDTO.builder()
                .id(order.getId().toString())
                .status(order.getStatus().name())
                .totalAmount(order.getTotalAmount())
                .items(itemDTOs)
                .build();
    }

    default ItemOrderDTO toItemDto(ItemOrder item) {
        if (item == null || item.getProduct() == null) return null;

        return ItemOrderDTO.builder()
                .productId(item.getProduct().getId().toString())
                .productName(item.getProduct().getName())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .build();
    }
}
