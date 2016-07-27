package main.com.urlshortner.service;

import main.com.urlshortner.dao.UserDataService;
import main.com.urlshortner.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserDataService userDataService;

    public Optional<String> createUser(String userId){
        if(userDataService.doesUserExists(userId)){
            return Optional.empty();
        }
        User newUser= new User(userId);
        if(!userDataService.addUser(newUser)){
            return Optional.empty();
        }
        return Optional.of(newUser.password());
    }

    public Optional<User> user(String authString){
        try {
            if (authString != null && authString.startsWith("Basic")) {
                final String[] values = getUsernameAndPasswordFromAuthString(authString);
                return userDataService.getUser(values[0], values[1]);
            }
            return Optional.empty();
        }catch (RuntimeException e){
            return Optional.empty();
        }
    }

    private String[] getUsernameAndPasswordFromAuthString(String authString) {
        String base64Credentials = authString.substring("Basic".length()).trim();
        String credentials = new String(Base64.getDecoder().decode(base64Credentials),
                Charset.forName("UTF-8"));
        return credentials.split(":", 2);
    }

}
