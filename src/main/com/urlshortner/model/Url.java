package main.com.urlshortner.model;

import com.google.common.base.Objects;
import main.com.urlshortner.utils.RandomNumberGeneratorUtil;
import main.com.urlshortner.utils.UrlShortnerUtility;

public class Url {

    private Integer id;
    private String userName;
    private String fullUrl;
    private String shortUrl;
    private Integer redirectType;

    public Url(String userName, String fullUrl, Integer redirectType) {
        this.userName = userName;
        this.id = RandomNumberGeneratorUtil.getUniqueRandomNumber();
        this.fullUrl = fullUrl;
        this.shortUrl = UrlShortnerUtility.encode(id);
        this.redirectType = redirectType;
    }

    public String fullUrl() {
        return fullUrl;
    }

    public String shortUrl() {
        return shortUrl;
    }

    public Integer id() {
        return id;
    }

    public Integer redirectType() {
        return redirectType;
    }

    public String userName() {
        return userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Url url = (Url) o;
        return Objects.equal(id, url.id) &&
                Objects.equal(userName, url.userName) &&
                Objects.equal(fullUrl, url.fullUrl) &&
                Objects.equal(shortUrl, url.shortUrl) &&
                Objects.equal(redirectType, url.redirectType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, userName, fullUrl, shortUrl, redirectType);
    }
}
