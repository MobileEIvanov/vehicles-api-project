package com.udacity.pricing.api;

import com.udacity.pricing.domain.price.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PricingControllerIntegrationTest {


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void requestPrice_withValidId_isSuccessful() {
        long vehicleId = 1;
        ResponseEntity<Price> responseEntity =
            this.restTemplate.getForEntity("http://localhost:" + port + "/services/price?vehicleId=" + vehicleId, Price.class);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void requestPrice_withoutId_failsWith_400() {
        ResponseEntity<Price> responseEntity =
            this.restTemplate.getForEntity("http://localhost:" + port + "/services/price", Price.class);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }


    @Test
    void requestPrice_withInvalidId_failsWith_404() {
        long vehicleId = -1;
        ResponseEntity<Price> responseEntity =
            this.restTemplate.getForEntity("http://localhost:" + port + "/services/price?vehicleId=" + vehicleId , Price.class);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}
