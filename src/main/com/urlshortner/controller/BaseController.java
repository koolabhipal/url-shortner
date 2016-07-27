package main.com.urlshortner.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    public static final String HELP_TEXT = "Use /account for creating an account (POST data must contain 'accountId' in the request JSON)<br>" +
            "Use /register for registering url's (POST data must contain 'url' in the request JSON). The request header must have the authentication header comprising of Basic auth (Basic 'base64Credentials')<br>"+
            "Examples :-<br>"+
            "curl -H \"Content-Type: application/json\" -X POST -d '{\"accountId\":\"sampleAccountId\"}' http://localhost:8080/account<br>"+
            "curl -H \"Content-Type: application/json\" -H \"Authorization:Basic cG9wOmNvRGo5eg==\" -X POST -d '{\"url\":\"http://google.com\",\"redirectType\":\"301\"}' http://localhost:8080/register<br>"+
            "Further more statistics of a specific user's url redirection can be obtained by using /statistic/{accountId}. The request header must have the authentication header comprising of Basic auth (Basic 'base64Credentials')<br>";


    @RequestMapping("/")
    public String index() {
        return "Welcome to url shortner !!!  Use '/help' for more info";
    }

    @RequestMapping(value = "/help", method = RequestMethod.GET)
    public String help() {
        return HELP_TEXT;
    }



}
