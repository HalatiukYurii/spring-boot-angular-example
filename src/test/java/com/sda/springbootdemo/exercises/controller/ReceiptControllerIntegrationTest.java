package com.sda.springbootdemo.exercises.controller;

import com.sda.springbootdemo.exercises.model.Receipt;
import com.sda.springbootdemo.exercises.repository.ReceiptRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ReceiptControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @MockBean
    private ReceiptRepository repository;

    private Receipt receipt;

    @Before
    public void setUp() {
        receipt = new Receipt("Tomek", LocalDateTime.now(), null);
        receipt.setId(UUID.randomUUID());
        when(repository.findById(receipt.getId()))
                .thenReturn(Optional.of(receipt));
    }

    @Test
    public void shouldReturnReceiptById() {
        given()
            .port(port)
            .log().all()
        .when()
            .get("/receipts/" + receipt.getId())
        .then()
            .log().all()
            .assertThat()
                .statusCode(OK.value())
                .body("buyer", containsString(receipt.getBuyer()));
    }
}
