package main.com.urlshortner.controller;

import main.com.urlshortner.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/account", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Object> createAccount(@RequestBody String inputJson){
        JSONObject jsonObject = new JSONObject(inputJson);
        String accountId = jsonObject.getString("accountId");
        Optional<String> accountPassword = userService.createUser(accountId);
        if(accountPassword.isPresent()){
            return new ResponseEntity<>("{\"success\":\"true\",\"description\":\"Your account is opened\",\"password\":\"" + accountPassword.get() + "\"}", HttpStatus.valueOf(201));
        }
        else {
            return new ResponseEntity<>("{\"success\":\"false\",\"description\":\"Account with that ID already exists\"}", HttpStatus.valueOf(409));
        }
    }


}
