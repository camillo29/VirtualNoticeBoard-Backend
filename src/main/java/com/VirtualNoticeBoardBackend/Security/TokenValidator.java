package com.VirtualNoticeBoardBackend.Security;

import com.VirtualNoticeBoardBackend.Model.User;
import com.VirtualNoticeBoardBackend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenValidator {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JWTUtil jwt;

    public boolean hasRole(String token, String role) {
        if (!jwt.isTokenExpired(token)) {
            User user = userRepository.findByEmail(jwt.getUsernameFromToken(token));
            if(user.getRole().getName().equals(role))
                return true;
            }else return false;
        return false;
    }

    public boolean hasExpired(String token){
        return jwt.isTokenExpired(token);
    }
}
