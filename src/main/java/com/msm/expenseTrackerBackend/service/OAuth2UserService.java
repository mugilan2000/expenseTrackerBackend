package com.msm.expenseTrackerBackend.service;

import com.msm.expenseTrackerBackend.model.User;
import com.msm.expenseTrackerBackend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OAuth2UserService {

    @Autowired
    private UserRepo userRepo;

    public User processUser(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        String username = oAuth2User.getAttribute("name");
        String provider = oAuth2User.getAttribute("provider");
        String googleId = oAuth2User.getAttribute("sub");

        Optional<User> user = userRepo.findByEmail(email);

        if(user.isPresent()){

            User existingUser = user.get();
            existingUser.setProvider("GOOGLE");
            existingUser.setProviderId(googleId);
            existingUser.setRole("USER");
            userRepo.save(existingUser);
            return existingUser;
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setProvider("GOOGLE");
        newUser.setProviderId(googleId);
        newUser.setRole("USER");
        userRepo.save(newUser);

        return newUser;
    }
}
