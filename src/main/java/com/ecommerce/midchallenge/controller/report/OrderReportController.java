package com.ecommerce.midchallenge.controller.report;

import com.ecommerce.midchallenge.dto.report.MonthlyRevenueDTO;
import com.ecommerce.midchallenge.dto.report.TopUserDTO;
import com.ecommerce.midchallenge.dto.report.UserAverageTicketDTO;
import com.ecommerce.midchallenge.service.report.OrderReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders/reports")
@RequiredArgsConstructor
public class OrderReportController {

    private final OrderReportService reportService;

    @GetMapping("/top-users")
    public ResponseEntity<List<TopUserDTO>> getTopUsers() {
        return ResponseEntity.ok(reportService.getTop5Users());
    }

    @GetMapping("/avg-ticket")
    public ResponseEntity<List<UserAverageTicketDTO>> getAvgTicket() {
        return ResponseEntity.ok(reportService.getAverageTicketByUser());
    }

    @GetMapping("/revenue")
    public ResponseEntity<MonthlyRevenueDTO> getMonthlyRevenue() {
        return ResponseEntity.ok(reportService.getMonthlyRevenue());
    }
}
