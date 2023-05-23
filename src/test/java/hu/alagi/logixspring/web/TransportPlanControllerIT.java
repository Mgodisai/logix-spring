package hu.alagi.logixspring.web;

import hu.alagi.logixspring.dto.MilestoneDelayDto;
import hu.alagi.logixspring.dto.TransportPlanDto;
import hu.alagi.logixspring.exception.DefaultErrorEntity;
import hu.alagi.logixspring.exception.ErrorCode;
import hu.alagi.logixspring.exception.ValidationErrorEntity;
import hu.alagi.logixspring.mapper.TransportPlanMapper;
import hu.alagi.logixspring.model.Milestone;
import hu.alagi.logixspring.model.Section;
import hu.alagi.logixspring.model.TransportPlan;
import hu.alagi.logixspring.security.LoginDto;
import hu.alagi.logixspring.service.MilestoneService;
import hu.alagi.logixspring.service.TransportPlanService;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransportPlanControllerIT {
   private static final String BASE_URI = "/api/transportPlans";
   public static final String LOGIN_URI = "/api/login";
   private static final String DELAY_URI = "/{id}/delay";
   private static final String PASS = "pass123";
   private static final Double OFFSET = 0.00001;
   private static final String TEST_ADDRESS_MANAGER = "aman";
   private static final String TEST_TRANSPORT_MANAGER = "tman";
   private final WebTestClient webTestClient;
   private String jwtTokenTransportManager;
   private final TransportPlanService transportPlanService;
   private final MilestoneService milestoneService;
   private final TransportPlanMapper transportPlanMapper;

   @Autowired
   TransportPlanControllerIT(WebTestClient webTestClient, TransportPlanService transportPlanService, MilestoneService milestoneService, TransportPlanMapper transportPlanMapper) {
      this.webTestClient = webTestClient;
      this.transportPlanService = transportPlanService;
      this.milestoneService = milestoneService;
      this.transportPlanMapper = transportPlanMapper;
   }

   @BeforeEach
   public void init() {
      jwtTokenTransportManager = login(TEST_TRANSPORT_MANAGER, PASS);
   }

   public String login(String username, String password) {
      return webTestClient.post()
              .uri(LOGIN_URI)
              .bodyValue(new LoginDto(username,password))
              .exchange()
              .expectBody(String.class)
              .returnResult()
              .getResponseBody();
   }

   @Test
   void testNonExistingTransportPlanId() {
      Long invalidTransportPlanId = -1L;
      Long validMilestoneId = 2L;
      MilestoneDelayDto milestoneDelayDto = new MilestoneDelayDto();
      milestoneDelayDto.setMilestoneId(validMilestoneId);
      milestoneDelayDto.setDelayInMinutes(10);

      Optional<TransportPlan> invalidTransportPlan = transportPlanService.findTransportPlanById(invalidTransportPlanId);
      assertThat(invalidTransportPlan).isEmpty();
      Optional<Milestone> validMilestoneBefore = milestoneService.findMilestoneById(validMilestoneId);
      assertThat(validMilestoneBefore).isNotEmpty();
      assertThat(validMilestoneBefore.get().getId()).isEqualTo(validMilestoneId);

      DefaultErrorEntity response = webTestClient.post()
              .uri(BASE_URI + DELAY_URI, invalidTransportPlanId)
              .headers(h -> h.setBearerAuth(jwtTokenTransportManager))
              .bodyValue(milestoneDelayDto)
              .exchange()
              .expectStatus()
              .isNotFound()
              .expectBody(DefaultErrorEntity.class)
              .returnResult()
              .getResponseBody();

      assertThat(response).isNotNull();
      assertThat(response.getErrorCode()).isEqualTo(ErrorCode.ENTITY_NOT_EXISTS);
      Optional<Milestone> validMilestoneAfter = milestoneService.findMilestoneById(validMilestoneId);
      assertThat(validMilestoneAfter).isNotEmpty();
      assertThat(validMilestoneAfter.get().getId()).isEqualTo(validMilestoneId);
      assertThat(validMilestoneBefore.get().getPlannedTime()).isEqualTo(validMilestoneAfter.get().getPlannedTime());
   }

   @Test
   void testNonExistingMilestoneId() {
      Long validTransportPlanId = 1L;
      Long invalidMilestoneId = -1L;
      MilestoneDelayDto milestoneDelayDto = new MilestoneDelayDto();
      milestoneDelayDto.setMilestoneId(invalidMilestoneId);
      milestoneDelayDto.setDelayInMinutes(10);

      Optional<TransportPlan> validTransportPlanBefore = transportPlanService.findTransportPlanById(validTransportPlanId);
      assertThat(validTransportPlanBefore).isNotEmpty();
      assertThat(validTransportPlanBefore.get().getId()).isEqualTo(validTransportPlanId);
      Optional<Milestone> invalidMilestone = milestoneService.findMilestoneById(invalidMilestoneId);
      assertThat(invalidMilestone).isEmpty();


      DefaultErrorEntity response = webTestClient.post()
              .uri(BASE_URI + DELAY_URI, validTransportPlanId)
              .headers(h -> h.setBearerAuth(jwtTokenTransportManager))
              .bodyValue(milestoneDelayDto)
              .exchange()
              .expectStatus()
              .isNotFound()
              .expectBody(DefaultErrorEntity.class)
              .returnResult()
              .getResponseBody();

      assertThat(response).isNotNull();
      assertThat(response.getErrorCode()).isEqualTo(ErrorCode.ENTITY_NOT_EXISTS);
      Optional<TransportPlan> validTransportPlanAfter = transportPlanService.findTransportPlanById(validTransportPlanId);
      assertThat(validTransportPlanAfter).isNotEmpty();
      assertThat(validTransportPlanAfter.get().getId()).isEqualTo(validTransportPlanId);
      assertThat(validTransportPlanBefore.get().getExpectedRevenue()).isEqualTo(validTransportPlanAfter.get().getExpectedRevenue());
   }

   @Test
   void testMismatchingTransportPlanAndMilestoneIds() {
      Long validTransportPlanId = 1L;
      Long validButMismatchingMilestoneId = 8L;
      MilestoneDelayDto milestoneDelayDto = new MilestoneDelayDto();
      milestoneDelayDto.setMilestoneId(validButMismatchingMilestoneId);
      milestoneDelayDto.setDelayInMinutes(10);

      Optional<TransportPlan> validTransportPlanBefore = transportPlanService.findTransportPlanById(validTransportPlanId);
      assertThat(validTransportPlanBefore).isNotEmpty();
      assertThat(validTransportPlanBefore.get().getId()).isEqualTo(validTransportPlanId);
      Optional<Milestone> validMilestoneBefore = milestoneService.findMilestoneById(validButMismatchingMilestoneId);
      assertThat(validMilestoneBefore).isNotEmpty();
      assertThat(validMilestoneBefore.get().getId()).isEqualTo(validButMismatchingMilestoneId);

      DefaultErrorEntity response = webTestClient.post()
              .uri(BASE_URI + DELAY_URI, validTransportPlanId)
              .headers(h -> h.setBearerAuth(jwtTokenTransportManager))
              .bodyValue(milestoneDelayDto)
              .exchange()
              .expectStatus()
              .isBadRequest()
              .expectBody(DefaultErrorEntity.class)
              .returnResult()
              .getResponseBody();

      assertThat(response).isNotNull();
      assertThat(response.getErrorCode()).isEqualTo(ErrorCode.ENTITY_ID_MISMATCH);
      Optional<TransportPlan> validTransportPlanAfter = transportPlanService.findTransportPlanById(validTransportPlanId);
      assertThat(validTransportPlanAfter).isNotEmpty();
      assertThat(validTransportPlanAfter.get().getId()).isEqualTo(validTransportPlanId);
      assertThat(validTransportPlanBefore.get().getExpectedRevenue()).isEqualTo(validTransportPlanAfter.get().getExpectedRevenue());
      Optional<Milestone> validMilestoneAfter = milestoneService.findMilestoneById(validButMismatchingMilestoneId);
      assertThat(validMilestoneAfter).isNotEmpty();
      assertThat(validMilestoneAfter.get().getId()).isEqualTo(validButMismatchingMilestoneId);
      assertThat(validMilestoneBefore.get().getPlannedTime()).isEqualTo(validMilestoneAfter.get().getPlannedTime());
   }

   @Test
   void testWithAddressManagerToken() {
      String addressManagerJwtToken = login(TEST_ADDRESS_MANAGER, PASS);
      Long validTransportPlanId = 1L;
      Long validMilestoneId = 1L;
      MilestoneDelayDto milestoneDelayDto = new MilestoneDelayDto();
      milestoneDelayDto.setMilestoneId(validMilestoneId);
      milestoneDelayDto.setDelayInMinutes(10);
      webTestClient.post()
              .uri(BASE_URI + DELAY_URI, validTransportPlanId)
              .headers(h -> h.setBearerAuth(addressManagerJwtToken))
              .contentType(MediaType.APPLICATION_JSON)
              .bodyValue(milestoneDelayDto)
              .exchange()
              .expectStatus().isForbidden();
   }

   @Test
   void testWithNegativeDelay() {
      Long validTransportPlanId = 1L;
      Long validMilestoneId = 1L;
      MilestoneDelayDto milestoneDelayDto = new MilestoneDelayDto();
      milestoneDelayDto.setMilestoneId(validMilestoneId);
      milestoneDelayDto.setDelayInMinutes(-10);
      ValidationErrorEntity response = webTestClient.post()
              .uri(BASE_URI + DELAY_URI, validTransportPlanId)
              .headers(h -> h.setBearerAuth(jwtTokenTransportManager))
              .contentType(MediaType.APPLICATION_JSON)
              .bodyValue(milestoneDelayDto)
              .exchange()
              .expectStatus()
              .isBadRequest()
              .expectBody(ValidationErrorEntity.class)
              .returnResult()
              .getResponseBody();
      assertThat(response).isNotNull();
      assertThat(response.getErrorCode()).isEqualTo(ErrorCode.VALIDATION);
   }

   @ParameterizedTest
   @MethodSource("provideTestDelayWithValidDataArgs")
   void testDelayWithValidData(Long validTransportPlanId, Long validMilestoneId, int delay, Double reduction) {
      MilestoneDelayDto milestoneDelayDto = new MilestoneDelayDto();
      milestoneDelayDto.setMilestoneId(validMilestoneId);
      milestoneDelayDto.setDelayInMinutes(delay);

      // It passes if repeating multiple times
      testRegisterDelayWithValidData(validTransportPlanId, milestoneDelayDto, reduction);
      testRegisterDelayWithValidData(validTransportPlanId, milestoneDelayDto, reduction);
      testRegisterDelayWithValidData(validTransportPlanId, milestoneDelayDto, reduction);
   }

   private static Stream<Arguments> provideTestDelayWithValidDataArgs() {
      return Stream.of(
              // FromMilestone delay, only one section affected
              Arguments.of(1L, 1L, 30, 5.0),

              // ToMilestone delay, next section should be also affected
              Arguments.of(1L, 2L, 30, 5.0),

              // FromMilestone delay in second section
              Arguments.of(1L, 3L, 30, 5.0),

              // ToMilestone delay in last section
              Arguments.of(1L, 6L, 30, 5.0),

              // longer delay
              Arguments.of(1L, 1L, 150, 20.0),

              // medium delay
              Arguments.of(1L, 1L, 70, 10.0),

              // shorter delay
              Arguments.of(1L, 1L, 29, 0.0),

              // no delay
              Arguments.of(1L, 1L, 0, 0.0)

      );
   }

   void testRegisterDelayWithValidData(Long validTransportPlanId, MilestoneDelayDto milestoneDelayDto, Double reductionPercent) {

      Optional<TransportPlan> validTransportPlanBefore = transportPlanService.findTransportPlanById(validTransportPlanId);
      assertThat(validTransportPlanBefore).isNotEmpty();
      assertThat(validTransportPlanBefore.get().getId()).isEqualTo(validTransportPlanId);
      Optional<Milestone> validFromMilestoneBefore = milestoneService.findMilestoneById(milestoneDelayDto.getMilestoneId());
      assertThat(validFromMilestoneBefore).isNotEmpty();
      assertThat(validFromMilestoneBefore.get().getId()).isEqualTo(milestoneDelayDto.getMilestoneId());

      Map<Long, LocalDateTime> plannedTimesOfMilestonesBefore = mapMilestonePlannedTimes(validTransportPlanBefore.get());

      TransportPlanDto responseTransportPlanDto = webTestClient.post()
              .uri(BASE_URI + DELAY_URI, validTransportPlanId)
              .headers(h -> h.setBearerAuth(jwtTokenTransportManager))
              .contentType(MediaType.APPLICATION_JSON)
              .bodyValue(milestoneDelayDto)
              .exchange()
              .expectStatus().isOk()
              .expectBody(TransportPlanDto.class)
              .returnResult()
              .getResponseBody();

      // TransportPlan asserts
      TransportPlan responseTransportPlan = transportPlanMapper.toTransportPlan(responseTransportPlanDto);
      assertThat(responseTransportPlan).isNotNull();
      assertThat(responseTransportPlan.getId()).isEqualTo(validTransportPlanId);
      assertThat(responseTransportPlan.getExpectedRevenue()).isCloseTo(validTransportPlanBefore.get().getExpectedRevenue()*(1-reductionPercent/100.0), Offset.offset(OFFSET));

      Optional<Milestone> validFromMilestoneAfter = milestoneService.findMilestoneById(milestoneDelayDto.getMilestoneId());
      assertThat(validFromMilestoneAfter).isNotEmpty();
      assertThat(validFromMilestoneAfter.get().getId()).isEqualTo(milestoneDelayDto.getMilestoneId());
      assertThat(validFromMilestoneAfter.get().getPlannedTime()).isEqualTo(validFromMilestoneBefore.get().getPlannedTime().plusMinutes(milestoneDelayDto.getDelayInMinutes()));

      List<Section> sections = responseTransportPlan.getSectionList();
      Map<Long, LocalDateTime> plannedTimesOfMilestonesAfter = mapMilestonePlannedTimes(responseTransportPlan);

      // Check the planned times milestone by milestone
      plannedTimesOfMilestonesBefore.forEach((milestoneId, plannedTimeBefore) -> {
         LocalDateTime plannedTimeAfter = plannedTimesOfMilestonesAfter.get(milestoneId);
         if (shouldChange(milestoneId, sections, milestoneDelayDto)) {
            assertThat(plannedTimeAfter).isEqualTo(plannedTimeBefore.plusMinutes(milestoneDelayDto.getDelayInMinutes()));
         } else {
            assertThat(plannedTimeAfter).isEqualTo(plannedTimeBefore);
         }
      });
   }

   private boolean shouldChange(Long requestedMilestoneId, List<Section> sections, MilestoneDelayDto change) {
      if (Objects.equals(requestedMilestoneId, change.getMilestoneId())) {
         return true;
      }

      sections.sort(Comparator.comparing(Section::getSectionOrderIndex));

      boolean isNextSectionAffected = false;
      for (Section section : sections) {
         if (isNextSectionAffected) {
            return section.getFromMilestone().getId().equals(requestedMilestoneId);
         }
         if (section.getFromMilestone().getId().equals(change.getMilestoneId())) {
            return section.getToMilestone().getId().equals(requestedMilestoneId);
         }
         if (section.getToMilestone().getId().equals(change.getMilestoneId())) {
            isNextSectionAffected = true;
         }
      }
      return false;
   }

   private Map<Long, LocalDateTime> mapMilestonePlannedTimes(TransportPlan transportPlan) {
      if (transportPlan == null) {
         throw new IllegalStateException("TransportPlan is null");
      }
      return transportPlan
                      .getSectionList()
                      .stream()
                      .flatMap(section -> Stream.of(section.getFromMilestone(), section.getToMilestone()))
                      .collect(Collectors.toMap(Milestone::getId, Milestone::getPlannedTime));
   }
}