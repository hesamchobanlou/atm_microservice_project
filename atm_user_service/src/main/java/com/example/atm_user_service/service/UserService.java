package com.example.atm_user_service.service;

import com.example.atm_user_service.model.UserEditRequest;
import com.example.atm_user_service.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDetails);
    UserDto getUserDetailsByUserId(String userId);
    UserDto changeUserName(String userId, UserEditRequest userEditRequest);
}
