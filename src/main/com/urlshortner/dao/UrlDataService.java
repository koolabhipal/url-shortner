package main.com.urlshortner.dao;

import com.google.common.collect.Iterables;
import main.com.urlshortner.model.Url;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UrlDataService {

    public static List<Url> storedUrls = new ArrayList<Url>();
    public static Map<Url, Integer> redirectionStatistics = new HashMap<>();

    public void persistUrl(Url url){
        storedUrls.add(url);
    }

    public Url getUrl(Integer id){
        return Iterables.getOnlyElement(
                storedUrls.stream()
                .filter(url -> url.id().equals(id))
                .collect(Collectors.toList())
        );
    }

    public void registerRedirection(Url url) {
        Integer currentCount = redirectionStatistics.get(url);
        redirectionStatistics.put(url, currentCount == null ? 1 : currentCount + 1);
    }

    public List<String> retreiveStatistic(String userName){
        return  redirectionStatistics.entrySet()
                .stream()
                .filter(entry -> entry.getKey().userName().equals(userName))
                .map(entry -> "\"" + entry.getKey().fullUrl() + "\"" + ":" + "\"" + entry.getValue() + "\"")
                .collect(Collectors.toList());
    }
}
