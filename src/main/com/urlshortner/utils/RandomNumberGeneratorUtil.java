package main.com.urlshortner.utils;

import java.util.Random;

public class RandomNumberGeneratorUtil {

    private static final Random RANDOM = new Random();
    private static final Integer MINIMUM = 99999999;

    public static Integer getUniqueRandomNumber(){
        return RANDOM.nextInt(Integer.MAX_VALUE - MINIMUM) + MINIMUM;
    }

}
