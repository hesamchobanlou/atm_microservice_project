package com.example.atm_service.controller;

import com.example.atm_service.model.*;
import com.example.atm_service.service.UserBankAccountService;
import com.example.atm_service.service.UserRestTemplateClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/atm")
public class ATMController {

    @Autowired
    UserBankAccountService userBankAccountService;

    @Autowired
    UserRestTemplateClient userRestTemplateClient;

    private static final String RESILIENCE4J_INSTANCE_NAME = "atm_service_circuit_breaker";
//    private static final String FALLBACK_METHOD = "fallback";

    @CircuitBreaker(name=RESILIENCE4J_INSTANCE_NAME)
    @RateLimiter(name="atm_calls_limit")
    @GetMapping(path="/balance")
    public ResponseEntity<UserBankAccount> bankAccountBalance(@PathVariable String userId) {
        UserBankAccount userBankAccount = userBankAccountService.bankAccountBalance(userId);

        if (userBankAccount != null) {
            return ResponseEntity.status(HttpStatus.OK).body(userBankAccount);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @CircuitBreaker(name=RESILIENCE4J_INSTANCE_NAME)
    @RateLimiter(name="atm_calls_limit")
    @PostMapping(path="/deposit")
    public ResponseEntity<UserBankAccount> bankAccountDeposit(@PathVariable String userId,
                                                              @RequestBody UserBankAccountDepositRequest userBankAccountDepositRequest) {
        UserBankAccount userBankAccount = userBankAccountService.bankAccountDeposit(userId, userBankAccountDepositRequest.getAmount());

        if (userBankAccount != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userBankAccount);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @CircuitBreaker(name=RESILIENCE4J_INSTANCE_NAME)
    @RateLimiter(name="atm_calls_limit")
    @PostMapping(path="/withdraw", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserBankAccount> bankAccountWithdraw(@PathVariable String userId,
                                                               @RequestBody UserBankAccountWithdrawRequest userBankAccountWithdrawRequest) {
            double withdrawAmount = userBankAccountWithdrawRequest.getAmount();

            if (withdrawAmount > 0) {
                try {
                    UserBankAccount userBankAccount = userBankAccountService.bankAccountWithdraw(userId, withdrawAmount);

                    if (userBankAccount != null) {
                        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userBankAccount);
                    } else {
                        return new ResponseEntity(HttpStatus.NOT_FOUND);
                    }
                } catch (Exception e) {
                    return new ResponseEntity(String.format("{\"message\": \"%s\"}", e.getMessage()), HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity("{\"message\": \"Please provide an amount greater than 0\"}", HttpStatus.BAD_REQUEST);
            }
    }

    @CircuitBreaker(name=RESILIENCE4J_INSTANCE_NAME)
    @RateLimiter(name="atm_calls_limit")
    @RequestMapping(path="/profile/edit", method=RequestMethod.PUT)
    public ResponseEntity<UserResponse> changeName(@PathVariable String userId,
                                                   @RequestBody UserEditRequest userEditRequest) {
        UserResponse userResponse = userRestTemplateClient.changeNameRequest(userId, userEditRequest);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userResponse);
    }

    @CircuitBreaker(name=RESILIENCE4J_INSTANCE_NAME)
    @RequestMapping(path="/test_circuit_breaker", method=RequestMethod.GET)
    public ResponseEntity testCircuitBreaker() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            // do nothing
        }

        return ResponseEntity.ok().body("");
    }

//    public ResponseEntity fallback() {
//        return ResponseEntity.internalServerError().body("{\"message\": \"fallback method invoked\"}");
//    }
}