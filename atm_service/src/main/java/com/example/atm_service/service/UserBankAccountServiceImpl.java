package com.example.atm_service.service;

import com.example.atm_service.data.UserBankAccountEntity;
import com.example.atm_service.data.UserEntity;
import com.example.atm_service.data.UserRepository;
import com.example.atm_service.model.UserBankAccount;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBankAccountServiceImpl implements UserBankAccountService {

    @Autowired
    UserRepository userRepository;

    public UserBankAccountServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserBankAccount bankAccountBalance(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity != null) {
            UserBankAccountEntity userBankAccountEntity = userEntity.getUserBankAccount();

            if (userBankAccountEntity != null) {
                ModelMapper modelMapper = new ModelMapper();
                modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

                UserBankAccount userBankAccount = modelMapper.map(userBankAccountEntity, UserBankAccount.class);

                return userBankAccount;
            }
        }

        return null;
    }

    @Override
    public UserBankAccount bankAccountDeposit(String userId, double amount) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity != null) {
            UserBankAccountEntity userBankAccountEntity = userEntity.getUserBankAccount();

            if (userBankAccountEntity != null) {
                userBankAccountEntity.setBalance(userBankAccountEntity.getBalance() + amount);
                userRepository.save(userEntity);

                ModelMapper modelMapper = new ModelMapper();
                modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

                UserBankAccount userBankAccount = modelMapper.map(userBankAccountEntity, UserBankAccount.class);

                return userBankAccount;
            }
        }

        return null;
    }

    @Override
    public UserBankAccount bankAccountWithdraw(String userId, double amount) throws Exception {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity != null) {
            UserBankAccountEntity userBankAccountEntity = userEntity.getUserBankAccount();

            if (userBankAccountEntity != null) {
                double currentFunds = userBankAccountEntity.getBalance();

                if ((currentFunds - amount) < 0) {
                    throw new Exception("Insufficient funds");
                } else {
                    userBankAccountEntity.setBalance(userBankAccountEntity.getBalance() - amount);
                    userRepository.save(userEntity);

                    ModelMapper modelMapper = new ModelMapper();
                    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

                    UserBankAccount userBankAccount = modelMapper.map(userBankAccountEntity, UserBankAccount.class);

                    return userBankAccount;
                }
            }
        }

        return null;
    }
}
