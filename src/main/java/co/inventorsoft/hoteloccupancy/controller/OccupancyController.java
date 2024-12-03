package co.inventorsoft.hoteloccupancy.controller;


import co.inventorsoft.hoteloccupancy.dto.OccupancyRequestDto;
import co.inventorsoft.hoteloccupancy.dto.OccupancyResponseDto;
import co.inventorsoft.hoteloccupancy.service.OccupancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/occupancy")
@RequiredArgsConstructor
public class OccupancyController {

    private final OccupancyService occupancyService;

    @PostMapping
    public OccupancyResponseDto getOccupancy(@RequestBody OccupancyRequestDto requestDto) {
        return occupancyService.getOccupancy(requestDto);
    }
}
