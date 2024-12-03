package co.inventorsoft.hoteloccupancy.service;


import co.inventorsoft.hoteloccupancy.dto.OccupancyRequestDto;
import co.inventorsoft.hoteloccupancy.dto.OccupancyResponseDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
public class OccupancyService {

    public OccupancyResponseDto getOccupancy(OccupancyRequestDto occupancyRequestDto) {

        List<BigDecimal> potentialGuests = occupancyRequestDto.getPotentialGuests();

        List<BigDecimal> premiumGuests = potentialGuests
                .stream()
                .filter(value -> value.compareTo(BigDecimal.valueOf(100)) >= 0)
                .sorted(Comparator.reverseOrder())
                .toList();

        List<BigDecimal> economyGuests = potentialGuests
                .stream()
                .filter(value -> value.compareTo(BigDecimal.valueOf(100)) < 0)
                .sorted(Comparator.reverseOrder())
                .toList();

        int premiumUsage = Math.min(occupancyRequestDto.getPremiumRooms(), premiumGuests.size());
        BigDecimal premiumRevenue = premiumGuests.subList(0, premiumUsage).stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int economyUsage = Math.min(occupancyRequestDto.getEconomyRooms(), economyGuests.size());
        BigDecimal economyRevenue = economyGuests.subList(0, economyUsage)
                .stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Handle upgrades from economy to premium rooms
        int remainingPremiumRooms = occupancyRequestDto.getPremiumRooms() - premiumUsage;
        if (remainingPremiumRooms > 0 && economyUsage == occupancyRequestDto.getEconomyRooms()) {
            List<BigDecimal> upgradableEconomyGuests = economyGuests.subList(0, remainingPremiumRooms);
            int upgradedGuests = Math.min(remainingPremiumRooms, upgradableEconomyGuests.size());
            BigDecimal upgradedRevenueChange = upgradableEconomyGuests.stream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            premiumUsage += upgradedGuests;
            premiumRevenue = premiumRevenue.add(upgradedRevenueChange);

            economyUsage -= upgradedGuests;
            economyRevenue = economyRevenue.subtract(upgradedRevenueChange);

            // After upgrade, check if economy rooms could be assigned to unallocated guests
            if (potentialGuests.size() > premiumUsage + economyUsage) {
                int remainingEconomyRooms = occupancyRequestDto.getEconomyRooms() - economyUsage;
                int remainingGuestsToAllocate = Math.min(remainingEconomyRooms, potentialGuests.size() - (premiumUsage + economyUsage));
                BigDecimal additionalEconomyRevenue = economyGuests.subList(upgradedGuests + economyUsage, economyGuests.size() - 1)
                        .stream()
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                economyUsage += remainingGuestsToAllocate;
                economyRevenue = economyRevenue.add(additionalEconomyRevenue);
            }
        }

        return OccupancyResponseDto.builder()
                .usagePremium(premiumUsage)
                .revenuePremium(premiumRevenue)
                .usageEconomy(economyUsage)
                .revenueEconomy(economyRevenue)
                .build();
    }
}
