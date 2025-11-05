package com.ecommerce.midchallenge.service.report;

import com.ecommerce.midchallenge.dto.report.MonthlyRevenueDTO;
import com.ecommerce.midchallenge.dto.report.TopUserDTO;
import com.ecommerce.midchallenge.dto.report.UserAverageTicketDTO;
import com.ecommerce.midchallenge.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderReportService {

    private final OrderRepository orderRepository;

    public List<TopUserDTO> getTop5Users() {
        return orderRepository.findTop5UsersByTotalSpent()
                .stream()
                .map(row -> new TopUserDTO(
                        (String) row[0],
                        (BigDecimal) row[1]
                ))
                .toList();
    }

    public List<UserAverageTicketDTO> getAverageTicketByUser() {
        return orderRepository.findAverageTicketByUser()
                .stream()
                .map(row -> new UserAverageTicketDTO(
                        (String) row[0],
                        (BigDecimal) row[1]
                ))
                .toList();
    }

    public MonthlyRevenueDTO getMonthlyRevenue() {
        BigDecimal total = orderRepository.getMonthlyRevenue();
        return new MonthlyRevenueDTO(total);
    }
}
