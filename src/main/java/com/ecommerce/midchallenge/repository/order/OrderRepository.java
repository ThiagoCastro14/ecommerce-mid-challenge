package com.ecommerce.midchallenge.repository.order;

import com.ecommerce.midchallenge.domain.order.Order;
import com.ecommerce.midchallenge.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    // 1) USER LISTA SEUS PRÓPRIOS PEDIDOS
    List<Order> findByUser(User user);

    // 2) TOP 5 USUÁRIOS QUE MAIS GASTARAM (SOMENTE PEDIDOS PAGOS)
    @Query(value = """
        SELECT u.name AS userName,
               SUM(o.total_amount) AS totalSpent
        FROM tb_orders o
        JOIN tb_users u ON o.user_id = u.id
        WHERE o.status = 'PAGO'
        GROUP BY u.id, u.name
        ORDER BY totalSpent DESC
        LIMIT 5
    """, nativeQuery = true)
    List<Object[]> findTop5UsersByTotalSpent();


    // 3) TICKET MÉDIO POR USUÁRIO (MÉDIA DO VALOR DOS PEDIDOS)
    @Query(value = """
        SELECT u.name AS userName,
               AVG(o.total_amount) AS avgTicket
        FROM tb_orders o
        JOIN tb_users u ON o.user_id = u.id
        WHERE o.status = 'PAGO'
        GROUP BY u.id, u.name
        ORDER BY avgTicket DESC
    """, nativeQuery = true)
    List<Object[]> findAverageTicketByUser();


    // 4) FATURAMENTO TOTAL DO MÊS ATUAL
    @Query(value = """
        SELECT COALESCE(SUM(o.total_amount), 0)
        FROM tb_orders o
        WHERE o.status = 'PAGO'
          AND MONTH(o.created_at) = MONTH(CURRENT_DATE())
          AND YEAR(o.created_at) = YEAR(CURRENT_DATE())
    """, nativeQuery = true)
    BigDecimal getMonthlyRevenue();
}
