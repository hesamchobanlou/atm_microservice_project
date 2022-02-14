package com.example.atm_service.service;

import com.example.atm_service.model.UserBankAccount;

public interface UserBankAccountService {
    UserBankAccount bankAccountBalance(String userId);
    UserBankAccount bankAccountDeposit(String userId, double amount);
    UserBankAccount bankAccountWithdraw(String userId, double amount) throws Exception;
}
