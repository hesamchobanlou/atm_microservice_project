package com.example.atm_user_service.controller;

import com.example.atm_user_service.model.User;
import com.example.atm_user_service.model.UserEditRequest;
import com.example.atm_user_service.model.UserResponse;
import com.example.atm_user_service.service.UserService;
import com.example.atm_user_service.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody User userDetails) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        UserDto createdUser = userService.createUser(userDto);

        UserResponse userResponse = modelMapper.map(createdUser, UserResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @RequestMapping(path="/{userId}/edit", method=RequestMethod.PUT)
    public ResponseEntity<UserResponse> changeNameRequest(@PathVariable String userId,
                                                          @RequestBody UserEditRequest userEditRequest) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto updatedUser = userService.changeUserName(userId, userEditRequest);

        UserResponse userResponse = modelMapper.map(updatedUser, UserResponse.class);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userResponse);
    }
}