package com.ecommerce.midchallenge.controller.order;

import com.ecommerce.midchallenge.dto.order.*;
import com.ecommerce.midchallenge.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    // USER cria pedido
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody CreateOrderRequest request) {
        return ResponseEntity.ok(service.createOrder(request));
    }

    // USER lista seus pedidos
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getMyOrders() {
        return ResponseEntity.ok(service.getMyOrders());
    }

    // USER paga pedido
    @PostMapping("/{id}/pay")
    public ResponseEntity<OrderDTO> payOrder(@PathVariable String id) {
        return ResponseEntity.ok(service.payOrder(id));
    }
}
