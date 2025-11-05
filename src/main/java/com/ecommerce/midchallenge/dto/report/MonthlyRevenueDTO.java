package com.ecommerce.midchallenge.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MonthlyRevenueDTO {
    private BigDecimal totalRevenue;
}
