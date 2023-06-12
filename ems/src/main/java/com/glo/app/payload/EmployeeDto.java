package com.glo.app.payload;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//Dto class to get request and send the response back to the client
//Validation constraints are given w.r.t fields
public class EmployeeDto {
    private long id;
    @NotEmpty
    @Size(min = 4, message = "Employee firstName must have minimum 4 character")
    private String firstName;
    @NotEmpty
    @Size(min = 3, message = "Employee lastName must have minimum 3 character")
    private String lastName;
    @Email
    private String email;
    @NotBlank
    private String department;
    @NotBlank
    private String position;
    @NotNull(message = "Salary cannot be null")
    private long salary;
}
