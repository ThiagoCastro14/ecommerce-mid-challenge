package com.ecommerce.midchallenge.service.order;

import com.ecommerce.midchallenge.domain.order.*;
import com.ecommerce.midchallenge.domain.product.Product;
import com.ecommerce.midchallenge.domain.user.User;
import com.ecommerce.midchallenge.dto.order.CreateOrderRequest;
import com.ecommerce.midchallenge.dto.order.ItemOrderDTO;
import com.ecommerce.midchallenge.dto.order.OrderDTO;
import com.ecommerce.midchallenge.exceptions.ResourceNotFoundException;
import com.ecommerce.midchallenge.mapper.order.OrderMapper;
import com.ecommerce.midchallenge.repository.order.OrderRepository;
import com.ecommerce.midchallenge.repository.order.ItemOrderRepository;
import com.ecommerce.midchallenge.repository.product.ProductRepository;
import com.ecommerce.midchallenge.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemOrderRepository itemOrderRepository;

    @Autowired
    private OrderMapper orderMapper;

    // CRIA PEDIDO (status PENDENTE)
    public OrderDTO createOrder(CreateOrderRequest request) {

        // Identifica o usuário autenticado
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        // Cria pedido inicial
        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.PENDENTE)
                .totalAmount(BigDecimal.ZERO)
                .build();

        order = orderRepository.save(order);

        BigDecimal total = BigDecimal.ZERO;
        List<ItemOrder> orderItems = new ArrayList<>();

        // Processa os itens
        for (ItemOrderDTO itemDTO : request.getItems()) {

            Product product = productRepository.findById(UUID.fromString(itemDTO.getProductId()))
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity()));
            total = total.add(itemTotal);

            ItemOrder item = ItemOrder.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemDTO.getQuantity())
                    .unitPrice(product.getPrice()) // salva o preço no momento da compra
                    .build();

            itemOrderRepository.save(item);
            orderItems.add(item);
        }

        // Salva total e itens
        order.setItems(orderItems);
        order.setTotalAmount(total);
        orderRepository.save(order);

        return orderMapper.toDto(order);
    }

    // LISTA PEDIDOS DO USUÁRIO LOGADO
    public List<OrderDTO> getMyOrders() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        return orderRepository.findByUser(user)
                .stream()
                .map(orderMapper::toDto)
                .toList();
    }

    // PAGA PEDIDO (valida estoque / desconta / cancela se faltar)
    public OrderDTO payOrder(String orderId) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Order order = orderRepository.findById(UUID.fromString(orderId))
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        // Verifica se é ADMIN ou dono do pedido
        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ADMIN"));
        
        if (!isAdmin && !order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Você não tem permissão para pagar este pedido.");
        }

        if (order.getStatus() != OrderStatus.PENDENTE) {
            throw new RuntimeException("Este pedido já foi processado (" + order.getStatus() + ")");
        }

        // Verificar estoque
        for (ItemOrder item : order.getItems()) {
            if (item.getProduct().getStockQuantity() < item.getQuantity()) {
                order.setStatus(OrderStatus.CANCELADO);
                orderRepository.save(order);
                throw new RuntimeException("Pedido cancelado: estoque insuficiente para o produto "
                        + item.getProduct().getName());
            }
        }

        // Descontar estoque
        for (ItemOrder item : order.getItems()) {
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
            productRepository.save(product);
        }

        // Finalizar como pago
        order.setStatus(OrderStatus.PAGO);
        orderRepository.save(order);

        return orderMapper.toDto(order);
    }
}
