package co.inventorsoft.hoteloccupancy.dto;

import co.inventorsoft.hoteloccupancy.validation.GreaterThanZero;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OccupancyRequestDto {

    @NotNull
    @PositiveOrZero
    private Integer premiumRooms;

    @NotNull
    @PositiveOrZero
    private Integer economyRooms;

    @NotNull
    @GreaterThanZero
    private List<BigDecimal> potentialGuests;
}
