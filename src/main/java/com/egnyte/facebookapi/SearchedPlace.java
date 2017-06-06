package com.egnyte.facebookapi;

import com.google.gson.annotations.Expose;

/**
 * Created by Vlad on 04.06.2017.
 */
public class SearchedPlace {

    private String searchedCountryName;
    private String searchedCityName;
    private String searchedPlace;

    private String id;
    @Expose
    private String name;
    private String city;
    private String cityId;
    private String country;
    private String countryCode;
    @Expose
    private Double latitude;
    @Expose
    private Double longitude;

    public SearchedPlace() {
    }

    public SearchedPlace(String searchedCountryName, String searchedCityName, String searchedPlace) {
        this.searchedCountryName = searchedCountryName;
        this.searchedCityName = searchedCityName;
        this.searchedPlace = searchedPlace;
    }

    @Override
    public String toString() {
        return "{" +
                "\"name\":" + getName() + '\'' +
                "latitude:" + getLatitude() + '\'' +
                ", longitude" + getLongitude() +
                '}';
    }

    public String getName() {
        return name;
    }

    private Double getLatitude() {
        return latitude;
    }

    private Double getLongitude() {
        return longitude;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
