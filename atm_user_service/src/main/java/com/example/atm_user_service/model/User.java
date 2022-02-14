package com.example.atm_user_service.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class User {
    @NotNull(message="first name cannot be null")
    private String firstName;

    @NotNull(message="last name cannot be null")
    private String lastName;

    @NotNull(message="pin cannot be null")
    @Pattern(regexp="[\\d]{4}", message="pin can only be numeric and 4-digits long")
    private String password; // or pin

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPin(String password) {
        this.password = password;
    }
}
