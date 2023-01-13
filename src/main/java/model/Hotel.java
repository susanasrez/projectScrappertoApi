package model;

import java.util.Map;

public class Hotel {
    String name;
    String location;
    String url;

    Map<String, String> services;

    Map<String, String> ratings;

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setServices(Map<String, String> services) {
        this.services = services;
    }

    public void setRatings(Map<String, String> ratings) {
        this.ratings = ratings;
    }
}
