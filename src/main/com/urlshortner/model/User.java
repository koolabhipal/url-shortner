package main.com.urlshortner.model;

import main.com.urlshortner.utils.RandomNumberGeneratorUtil;

public class User {

    private String userName;
    private String password;

    public User(String userName) {
        this.userName = userName;
        this.password = generatePassword();
    }

    private String generatePassword(){
        return encode(RandomNumberGeneratorUtil.getUniqueRandomNumber());
    }

    private String encode(Integer num) {
        String alphabets = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Integer BASE = alphabets.length();
        StringBuilder sb = new StringBuilder();
        while ( num > 0 ) {
            sb.append(alphabets.charAt(num % BASE));
            num /= BASE;
        }
        return sb.reverse().toString();
    }

    public String userName() {
        return userName;
    }

    public String password() {
        return password;
    }
}
