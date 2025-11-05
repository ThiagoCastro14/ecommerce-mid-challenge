package com.ecommerce.midchallenge.repository.order;

import com.ecommerce.midchallenge.domain.order.ItemOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemOrderRepository extends JpaRepository<ItemOrder, UUID> {
}
