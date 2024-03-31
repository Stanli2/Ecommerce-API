package com.ike.ecommerce.api.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginBody {

    @NotNull
    private String username;
    @NotNull
    private String password;
}
