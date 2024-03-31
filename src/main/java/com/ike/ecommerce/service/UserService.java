package com.ike.ecommerce.service;

import com.ike.ecommerce.api.model.LoginBody;
import com.ike.ecommerce.api.model.RegistrationBody;
import com.ike.ecommerce.exception.UserAlreadyExistException;
import com.ike.ecommerce.model.LocalUser;
import com.ike.ecommerce.model.dao.LocalUserDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private LocalUserDao localUserDao;
    private EncryptionService encryptionService;
    private JWTService jwtService;

    public LocalUser register(RegistrationBody registrationBody) throws UserAlreadyExistException {
        if(localUserDao.findByEmailContainingIgnoreCase(registrationBody.getEmail()).isPresent()
                || localUserDao.findByUsernameContainingIgnoreCase(registrationBody.getUserName()).isPresent()){
            throw new UserAlreadyExistException();
        }
        LocalUser user = new LocalUser();
        user.setUsername(registrationBody.getUserName());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        user.setEmail(registrationBody.getEmail());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());

        return localUserDao.save(user);
    }

    public String loginUser(LoginBody loginBody){
        Optional<LocalUser> opUser = localUserDao.findByUsernameContainingIgnoreCase(loginBody.getUsername());
        if(opUser.isPresent()){
            LocalUser localUser = opUser.get();
            if(encryptionService.verifyPassword(loginBody.getPassword(), localUser.getPassword())) {
                return jwtService.generateJWT(localUser);
            }
        }
        return null;
    }
}
