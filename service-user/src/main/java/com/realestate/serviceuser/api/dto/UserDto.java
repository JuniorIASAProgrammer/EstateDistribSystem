package com.realestate.serviceuser.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String repeatPassword;
    private String phone;
    private String role;
}
