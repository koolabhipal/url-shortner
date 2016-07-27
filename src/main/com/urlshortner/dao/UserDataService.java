package main.com.urlshortner.dao;

import com.google.common.collect.Iterables;
import main.com.urlshortner.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserDataService {

    public static List<User> storedUsers = new ArrayList<>();

    public boolean addUser(User user){
        storedUsers.add(user);
        return true;  //false in case of failure to add w.r.t. some DB layer instead of keeping in application memory
    }

    public boolean doesUserExists(String userName){
        return storedUsers.stream().anyMatch(user -> user.userName().equals(userName));
    }

    public Optional<User> getUser(String userName, String password) {
        try{
            return Optional.of(Iterables.getOnlyElement(
                    storedUsers.stream()
                    .filter(u -> u.userName().equals(userName) && u.password().equals(password))
                    .collect(Collectors.toList())));
        }catch (RuntimeException e){
            return Optional.empty();
        }
    }

}
