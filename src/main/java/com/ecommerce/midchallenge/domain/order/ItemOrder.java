package com.ecommerce.midchallenge.domain.order;

import com.ecommerce.midchallenge.domain.product.Product;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tb_item_order",
       indexes = {
           @Index(name = "idx_item_product", columnList = "product_id"),
           @Index(name = "idx_item_order", columnList = "order_id")
       })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemOrder {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "CHAR(36)") 
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;
}
