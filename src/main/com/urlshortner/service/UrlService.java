package main.com.urlshortner.service;

import com.google.common.base.Joiner;
import main.com.urlshortner.model.Url;
import main.com.urlshortner.utils.UrlShortnerUtility;
import main.com.urlshortner.dao.UrlDataService;
import main.com.urlshortner.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UrlService {

    @Autowired
    private UrlDataService urlDataService;

    public String registerUrl(User user, String urlToRegister, Integer redirectType) {
        Url newUrl = new Url(user.userName(), urlToRegister, redirectType == null ? 302 : redirectType);
        urlDataService.persistUrl(newUrl);
        return newUrl.shortUrl();
    }

    public Url fetchUrl(String shortUrl) {
        Integer id = UrlShortnerUtility.decode(shortUrl);
        return urlDataService.getUrl(id);
    }

    public void registerRedirection(Url url) {
        urlDataService.registerRedirection(url);
    }

    public String retreiveStatistics(User user){
        List<String> statisticReports = urlDataService.retreiveStatistic(user.userName());
        return "{" + Joiner.on(",").join(statisticReports) + "}";
    }
}
