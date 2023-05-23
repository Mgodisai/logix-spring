package hu.alagi.logixspring.web;

import hu.alagi.logixspring.security.LoginDto;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransportPlanControllerIT {
   private static final String BASE_URI = "/api/transportPlans";
   private static final String PASS = "pass123";
   private static final String TEST_ADDRESS_MANAGER = "aman";
   private static final String TEST_TRANSPORT_MANAGER = "tman";

   private final WebTestClient webTestClient;

   private String jwtTokenTransportManager;

   TransportPlanControllerIT(WebTestClient webTestClient) {
      this.webTestClient = webTestClient;
   }

   @BeforeEach
   public void init() {
      jwtTokenTransportManager = webTestClient.post()
            .uri("/api/login")
            .bodyValue(new LoginDto(TEST_TRANSPORT_MANAGER,PASS))
            .exchange()
            .expectBody(String.class)
            .returnResult()
            .getResponseBody();
   }
}