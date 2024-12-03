package co.inventorsoft.hoteloccupancy.e2e;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OccupancySystemTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getOccupancy() throws Exception {
        mockMvc.perform(
                        post("/occupancy")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                                {
                                                             "premiumRooms": 7,
                                                             "economyRooms": 5,
                                                             "potentialGuests": [
                                                                 23,
                                                                 45,
                                                                 155,
                                                                 374,
                                                                 22,
                                                                 99.99,
                                                                 100,
                                                                 101,
                                                                 115,
                                                                 209
                                                             ]
                                                         }
                                                """
                                )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usagePremium").value(6))
                .andExpect(jsonPath("$.revenuePremium").value(1054))
                .andExpect(jsonPath("$.usageEconomy").value(4))
                .andExpect(jsonPath("$.revenueEconomy").value(189.99));
    }
}
