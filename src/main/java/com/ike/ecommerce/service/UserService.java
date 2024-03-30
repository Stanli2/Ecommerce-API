package com.ike.ecommerce.service;

import com.ike.ecommerce.api.model.RegistrationBody;
import com.ike.ecommerce.exception.UserAlreadyExistException;
import com.ike.ecommerce.model.LocalUser;
import com.ike.ecommerce.model.dao.LocalUserDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private LocalUserDao localUserDao;

    public LocalUser register(RegistrationBody registrationBody) throws UserAlreadyExistException {
        if(localUserDao.findByEmailContainingIgnoreCase(registrationBody.getEmail()).isPresent()
                || localUserDao.findByUsernameContainingIgnoreCase(registrationBody.getUserName()).isPresent()){
            throw new UserAlreadyExistException();
        }
        LocalUser user = new LocalUser();
        user.setUsername(registrationBody.getUserName());
        user.setPassword(registrationBody.getPassword());
        user.setEmail(registrationBody.getEmail());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());

        return localUserDao.save(user);
    }
}
