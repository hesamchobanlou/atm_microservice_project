package com.example.atm_service.controller;

import com.example.atm_service.model.UserBankAccount;
import com.example.atm_service.model.UserBankAccountDepositRequest;
import com.example.atm_service.model.UserBankAccountWithdrawRequest;
import com.example.atm_service.service.UserBankAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Produces;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/{userId}/atm")
public class ATMController {

    @Autowired
    UserBankAccountService userBankAccountService;

    @GetMapping(path="/balance")
    public ResponseEntity<UserBankAccount> bankAccountBalance(@PathVariable String userId) {
        UserBankAccount userBankAccount = userBankAccountService.bankAccountBalance(userId);

        if (userBankAccount != null) {
            return ResponseEntity.status(HttpStatus.OK).body(userBankAccount);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

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
}