package com.fullcycle.subscription.infrastructure.rest;

import com.fullcycle.subscription.ControllerTest;
import com.fullcycle.subscription.application.Presenter;
import com.fullcycle.subscription.application.subscription.CancelSubscription;
import com.fullcycle.subscription.application.subscription.CreateSubscription;
import com.fullcycle.subscription.domain.subscription.SubscriptionId;
import com.fullcycle.subscription.domain.subscription.status.CanceledSubscriptionStatus;
import com.fullcycle.subscription.infrastructure.rest.controllers.SubscriptionRestController;
import com.fullcycle.subscription.infrastructure.rest.models.res.CancelSubscriptionResponse;
import com.fullcycle.subscription.infrastructure.rest.models.res.CreateSubscriptionResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.fullcycle.subscription.ApiTest.admin;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest(controllers = SubscriptionRestController.class)
public class SubscriptionRestApiTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CreateSubscription createSubscription;

    @MockBean
    private CancelSubscription cancelSubscription;

    @Captor
    private ArgumentCaptor<CreateSubscription.Input> createSubscriptionInputCaptor;

    @Captor
    private ArgumentCaptor<CancelSubscription.Input> cancelSubscriptionInputCaptor;

    @Test
    public void givenValidInput_whenCreateSuccessfully_shouldReturnSubscriptionId() throws Exception {
        // given
        var expectedAccountId = "123";
        var expectedPlanId = 123L;
        var expectedSubscriptionId = new SubscriptionId("SUB123");

        when(createSubscription.execute(any(), any())).thenAnswer(call -> {
            Presenter<CreateSubscription.Output, CreateSubscriptionResponse> p = call.getArgument(1);
            return p.apply(new CreateSubscriptionTestOutput(expectedSubscriptionId));
        });

        var json = """
                {
                    "plan_id": %s
                }
                """.formatted(expectedPlanId);

        // when
        var aRequest = post("/subscriptions")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json)
                .with(admin(expectedAccountId));

        var aResponse = this.mvc.perform(aRequest);

        // then
        aResponse
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/subscriptions/" + expectedSubscriptionId.value()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.subscription_id").value(equalTo(expectedSubscriptionId.value())));

        verify(createSubscription, times(1)).execute(createSubscriptionInputCaptor.capture(), any());

        var actualRequest = createSubscriptionInputCaptor.getValue();

        Assertions.assertEquals(expectedPlanId, actualRequest.planId());
        Assertions.assertEquals(expectedAccountId, actualRequest.accountId());
    }

    @Test
    public void givenValidAccountId_whenCanceledSuccessfully_shouldReturnNewSubscriptionStatus() throws Exception {
        // given
        var expectedAccountId = "123";
        var expectedStatus = CanceledSubscriptionStatus.CANCELED;
        var expectedSubscriptionId = new SubscriptionId("SUB123");

        when(cancelSubscription.execute(any(), any())).thenAnswer(call -> {
            Presenter<CancelSubscription.Output, CancelSubscriptionResponse> p = call.getArgument(1);
            return p.apply(new CancelSubscriptionTestOutput(expectedSubscriptionId, expectedStatus));
        });

        // when
        var aRequest = put("/subscriptions/active/cancel")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(admin(expectedAccountId));

        var aResponse = this.mvc.perform(aRequest);

        // then
        aResponse
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.subscription_id").value(equalTo(expectedSubscriptionId.value())))
                .andExpect(jsonPath("$.subscription_status").value(equalTo(expectedStatus)));

        verify(cancelSubscription, times(1)).execute(cancelSubscriptionInputCaptor.capture(), any());

        var actualRequest = cancelSubscriptionInputCaptor.getValue();

        Assertions.assertEquals(expectedAccountId, actualRequest.accountId());
    }


    record CreateSubscriptionTestOutput(
            SubscriptionId subscriptionId
    ) implements CreateSubscription.Output {
    }

    record CancelSubscriptionTestOutput(
            SubscriptionId subscriptionId,
            String subscriptionStatus
    ) implements CancelSubscription.Output {
    }
}