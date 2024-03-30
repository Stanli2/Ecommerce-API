package com.ike.ecommerce.api.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegistrationBody {

    @NotNull
    private String userName;
    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")
    private String password;
    @Email
    private String email;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;

}
