package com.egnyte.facebookapi;

import org.springframework.social.facebook.api.Location;

/**
 * Created by Vlad on 04.06.2017.
 */
public class SearchedPlace {

    private String searchedCountryName;
    private String searchedCityName;
    private String searchedPlace;

    private String placeName;
    private Location location;

    public SearchedPlace() {
    }

    public SearchedPlace(String searchedCountryName, String searchedCityName, String searchedPlace) {
        this.searchedCountryName = searchedCountryName;
        this.searchedCityName = searchedCityName;
        this.searchedPlace = searchedPlace;
    }

    public String getSearchedCountryName() {
        return searchedCountryName;
    }

    public void setSearchedCountryName(String searchedCountryName) {
        this.searchedCountryName = searchedCountryName;
    }

    public String getSearchedCityName() {
        return searchedCityName;
    }

    public void setSearchedCityName(String searchedCityName) {
        this.searchedCityName = searchedCityName;
    }

    public String getSearchedPlace() {
        return searchedPlace;
    }

    public void setSearchedPlace(String searchedPlace) {
        this.searchedPlace = searchedPlace;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "{" +
                "\"name\":" + getPlaceName() + '\'' +
                "latitude:" + location.getLatitude() + '\'' +
                ", longitude" + location.getLongitude() +
                '}';
    }
}
