package co.inventorsoft.hoteloccupancy.service;

import co.inventorsoft.hoteloccupancy.dto.OccupancyRequestDto;
import co.inventorsoft.hoteloccupancy.dto.OccupancyResponseDto;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class OccupancyServiceTest {

    @InjectMocks
    private OccupancyService occupancyService;

    @ParameterizedTest
    @MethodSource("getOccupancyArgsProvider")
    void getOccupancy(OccupancyRequestDto request, OccupancyResponseDto expected) {
        assertThat(occupancyService.getOccupancy(request))
                .usingRecursiveComparison(
                        RecursiveComparisonConfiguration.builder()
                                .withComparatorForType(BigDecimal::compareTo, BigDecimal.class)
                                .build()
                )
                .isEqualTo(expected);
    }

    private static Stream<Arguments> getOccupancyArgsProvider() {
        return Stream.of(
                Arguments.of(
                        OccupancyRequestDto.builder()
                                .premiumRooms(3)
                                .economyRooms(3)
                                .potentialGuests(List.of(
                                        BigDecimal.valueOf(23),
                                        BigDecimal.valueOf(45),
                                        BigDecimal.valueOf(155),
                                        BigDecimal.valueOf(374),
                                        BigDecimal.valueOf(22),
                                        BigDecimal.valueOf(99.99),
                                        BigDecimal.valueOf(100),
                                        BigDecimal.valueOf(101),
                                        BigDecimal.valueOf(115),
                                        BigDecimal.valueOf(209)
                                ))
                                .build(),
                        OccupancyResponseDto.builder()
                                .usagePremium(3)
                                .revenuePremium(BigDecimal.valueOf(738))
                                .usageEconomy(3)
                                .revenueEconomy(BigDecimal.valueOf(167.99))
                                .build()
                ),
                Arguments.of(
                        OccupancyRequestDto.builder()
                                .premiumRooms(7)
                                .economyRooms(5)
                                .potentialGuests(List.of(
                                        BigDecimal.valueOf(23),
                                        BigDecimal.valueOf(45),
                                        BigDecimal.valueOf(155),
                                        BigDecimal.valueOf(374),
                                        BigDecimal.valueOf(22),
                                        BigDecimal.valueOf(99.99),
                                        BigDecimal.valueOf(100),
                                        BigDecimal.valueOf(101),
                                        BigDecimal.valueOf(115),
                                        BigDecimal.valueOf(209)
                                ))
                                .build(),
                        OccupancyResponseDto.builder()
                                .usagePremium(6)
                                .revenuePremium(BigDecimal.valueOf(1054))
                                .usageEconomy(4)
                                .revenueEconomy(BigDecimal.valueOf(189.99))
                                .build()
                ),
                Arguments.of(
                        OccupancyRequestDto.builder()
                                .premiumRooms(2)
                                .economyRooms(7)
                                .potentialGuests(List.of(
                                        BigDecimal.valueOf(23),
                                        BigDecimal.valueOf(45),
                                        BigDecimal.valueOf(155),
                                        BigDecimal.valueOf(374),
                                        BigDecimal.valueOf(22),
                                        BigDecimal.valueOf(99.99),
                                        BigDecimal.valueOf(100),
                                        BigDecimal.valueOf(101),
                                        BigDecimal.valueOf(115),
                                        BigDecimal.valueOf(209)
                                ))
                                .build(),
                        OccupancyResponseDto.builder()
                                .usagePremium(2)
                                .revenuePremium(BigDecimal.valueOf(583))
                                .usageEconomy(4)
                                .revenueEconomy(BigDecimal.valueOf(189.99))
                                .build()
                ),
                Arguments.of(
                        OccupancyRequestDto.builder()
                                .premiumRooms(7)
                                .economyRooms(2)
                                .potentialGuests(List.of(
                                        BigDecimal.valueOf(23),
                                        BigDecimal.valueOf(45),
                                        BigDecimal.valueOf(155),
                                        BigDecimal.valueOf(374),
                                        BigDecimal.valueOf(22),
                                        BigDecimal.valueOf(99.99),
                                        BigDecimal.valueOf(100),
                                        BigDecimal.valueOf(101),
                                        BigDecimal.valueOf(115),
                                        BigDecimal.valueOf(209)
                                ))
                                .build(),
                        OccupancyResponseDto.builder()
                                .usagePremium(7)
                                .revenuePremium(BigDecimal.valueOf(1153.99))
                                .usageEconomy(2)
                                .revenueEconomy(BigDecimal.valueOf(68))
                                .build()
                ),
                Arguments.of(
                        OccupancyRequestDto.builder()
                                .premiumRooms(5)
                                .economyRooms(7)
                                .potentialGuests(List.of())
                                .build(),
                        OccupancyResponseDto.builder()
                                .usagePremium(0)
                                .revenuePremium(BigDecimal.valueOf(0))
                                .usageEconomy(0)
                                .revenueEconomy(BigDecimal.valueOf(0))
                                .build()
                )
        );
    }
}