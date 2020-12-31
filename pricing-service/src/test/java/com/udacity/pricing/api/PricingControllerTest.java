package com.udacity.pricing.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest(PricingController.class)
class PricingControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Test
    void callingGetPricing_withVehicleID_isSuccessful() throws Exception{
        long vehicleId = 1;

        mockMvc.perform(get("/services/price")
            .param("vehicleId", String.valueOf(vehicleId)))
            .andExpect(status().isOk());
    }

    @Test
    void callingGetPricing_withoutVehicleID_fails() throws Exception{

        mockMvc.perform(get("/services/price"))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void callingGetPricing_withInvalidVehicleID_throwsPricingException() throws Exception{
        long vehicleId = -1;

        mockMvc.perform(get("/services/price")
            .param("vehicleId", String.valueOf(vehicleId)))
            .andExpect(status().isNotFound())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException));

    }

}
