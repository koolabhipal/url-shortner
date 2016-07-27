package main.com.urlshortner.controller;

import main.com.urlshortner.model.Url;
import main.com.urlshortner.model.User;
import main.com.urlshortner.service.UrlService;
import main.com.urlshortner.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
public class UrlController {

    @Autowired
    private UrlService urlService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Object> registerUrl(@RequestBody String inputJson, @RequestHeader(value = "authorization") String authString){
        Optional<User> user = userService.user(authString);
        if(!user.isPresent()){
            return new ResponseEntity<>("{\"error\":\"User not authenticated\"}", HttpStatus.valueOf(403));
        }
        JSONObject jsonObject = new JSONObject(inputJson);
        String urlToRegister = jsonObject.getString("url");
        Integer redirectType = jsonObject.getInt("redirectType");
        return new ResponseEntity<>("{\"shortUrl\":\"" + "localhost:8080/" + urlService.registerUrl(user.get(), urlToRegister, redirectType) + "\"}", HttpStatus.valueOf(202));
    }

    @RequestMapping(value = "/sh/{shortUrl}", method = RequestMethod.GET)
    public ResponseEntity<Object> redirect(@PathVariable(value = "shortUrl") String shortUrl) throws URISyntaxException {
        Url url = urlService.fetchUrl(shortUrl);
        urlService.registerRedirection(url);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(new URI(url.fullUrl()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.valueOf(url.redirectType()));
    }

    @RequestMapping(value = "/statistic/{accountId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Object> statistic(@PathVariable(value = "accountId") String userName, @RequestHeader(value = "authorization") String authString){
        Optional<User> user = userService.user(authString);
        if(!user.isPresent()){
            return new ResponseEntity<>("{\"error\":\"User not authenticated\"}", HttpStatus.valueOf(403));
        }
        if(!user.get().userName().equals(userName)){
            return new ResponseEntity<>("{\"error\":\"User not authenticated\"}", HttpStatus.valueOf(403));
        }
        return new ResponseEntity<>(urlService.retreiveStatistics(user.get()), HttpStatus.valueOf(200));
    }

}
