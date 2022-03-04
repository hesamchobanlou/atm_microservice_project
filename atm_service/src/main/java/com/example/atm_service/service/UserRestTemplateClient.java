package com.example.atm_service.service;

import com.example.atm_service.model.UserEditRequest;
import com.example.atm_service.model.UserResponse;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserRestTemplateClient {

    @Autowired
    RestTemplate restTemplate;

    @Retry(name="atm_user_service")
    @Bulkhead(name="atm_user_service")
    public UserResponse changeNameRequest(String userId, UserEditRequest userEditRequest) {
        HttpEntity<UserEditRequest> requestEntity = new HttpEntity<UserEditRequest>(userEditRequest);

        ResponseEntity<UserResponse> restExchange =
                restTemplate.exchange(
                        "http://atm-user-service/users/{userId}/edit",
                        HttpMethod.PUT,
                        requestEntity,
                        UserResponse.class,
                        userId);

        return restExchange.getBody();
    }
}
