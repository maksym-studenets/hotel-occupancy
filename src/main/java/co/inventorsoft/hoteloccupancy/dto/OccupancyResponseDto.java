package co.inventorsoft.hoteloccupancy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OccupancyResponseDto {

    private int usagePremium;
    private BigDecimal revenuePremium;
    private int usageEconomy;
    private BigDecimal revenueEconomy;
}
