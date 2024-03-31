package com.ike.ecommerce.api.controller.auth;

import com.ike.ecommerce.api.model.LoginBody;
import com.ike.ecommerce.api.model.LoginResponse;
import com.ike.ecommerce.api.model.RegistrationBody;
import com.ike.ecommerce.exception.UserAlreadyExistException;
import com.ike.ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AunthenticationController {


    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegistrationBody registrationBody) {
        try {
            userService.register(registrationBody);
            return ResponseEntity.ok().build();
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody){
        try{
            String jwt = userService.loginUser(loginBody);
            if(jwt == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }else{
                LoginResponse loginResponse = new LoginResponse();
                loginResponse.setJwt(jwt);
                return ResponseEntity.ok(loginResponse);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
