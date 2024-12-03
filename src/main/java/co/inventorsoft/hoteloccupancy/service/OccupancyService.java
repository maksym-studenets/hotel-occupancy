package co.inventorsoft.hoteloccupancy.service;


import co.inventorsoft.hoteloccupancy.dto.OccupancyRequestDto;
import co.inventorsoft.hoteloccupancy.dto.OccupancyResponseDto;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class OccupancyService {

    public OccupancyResponseDto getOccupancy(OccupancyRequestDto occupancyRequestDto) {

        List<Double> potentialGuests = occupancyRequestDto.getPotentialGuests();

        List<Double> premiumGuests = potentialGuests
                .stream()
                .filter(value -> value >= 100.0)
                .sorted(Comparator.reverseOrder())
                .toList();

        List<Double> economyGuests = potentialGuests
                .stream()
                .filter(value -> value < 100.0)
                .sorted(Comparator.reverseOrder())
                .toList();

        int premiumUsage = Math.min(occupancyRequestDto.getPremiumRooms(), premiumGuests.size());
        double premiumRevenue = premiumGuests.subList(0, premiumUsage).stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        int economyUsage = Math.min(occupancyRequestDto.getEconomyRooms(), economyGuests.size());
        double economyRevenue = economyGuests.subList(0, economyUsage)
                .stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        // Handle upgrades from economy to premium rooms
        int remainingPremiumRooms = occupancyRequestDto.getPremiumRooms() - premiumUsage;
        if (remainingPremiumRooms > 0 && economyUsage == occupancyRequestDto.getEconomyRooms()) {
            List<Double> upgradableEconomyGuests = economyGuests.subList(0, remainingPremiumRooms);
            int upgradedGuests = Math.min(remainingPremiumRooms, upgradableEconomyGuests.size());
            double upgradedRevenueChange = upgradableEconomyGuests.stream()
                    .mapToDouble(Double::doubleValue)
                    .sum();

            premiumUsage += upgradedGuests;
            premiumRevenue += upgradedRevenueChange;

            economyUsage -= upgradedGuests;
            economyRevenue -= upgradedRevenueChange;

            // After upgrade, check if economy rooms could be assigned to unallocated guests
            if (potentialGuests.size() > premiumUsage + economyUsage) {
                int remainingEconomyRooms = occupancyRequestDto.getEconomyRooms() - economyUsage;
                int remainingGuestsToAllocate = Math.min(remainingEconomyRooms, potentialGuests.size() - (premiumUsage + economyUsage));
                double additionalEconomyRevenie = economyGuests.subList(upgradedGuests + economyUsage, economyGuests.size() - 1)
                        .stream()
                        .mapToDouble(Double::doubleValue)
                        .sum();

                economyUsage += remainingGuestsToAllocate;
                economyRevenue += additionalEconomyRevenie;
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
