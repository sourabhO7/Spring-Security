package com.glo.app.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//DTO class to register the new user in the system
public class RegisterDto {
    private long id;
    private String name;
    private String email;
    private String username;
    private String password;
}
