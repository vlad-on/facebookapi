package com.egnyte.facebookapi.controller;

import com.egnyte.facebookapi.SearchedPlace;
import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.social.facebook.api.Location;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
@PropertySource("application.properties")
public class HelloController {

    @Value("${spring.social.facebook.app-id}")
    private String appId;
    @Value("${spring.social.facebook.app-secret}")
    private String appSecret;
    private final String ACCESS_TOKEN = appId + "|" + appSecret;

    private RestTemplate restTemplate = new RestTemplate();

//    private Facebook facebook;
//    private ConnectionRepository connectionRepository;
//
//    public HelloController(Facebook facebook, ConnectionRepository connectionRepository) {
//        this.facebook = facebook;
//        this.connectionRepository = connectionRepository;
//    }

    @GetMapping
    public String helloFacebook(Model model) {
//        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
//            return "redirect:https://graph.facebook.com/v2.9/search?q=poznan&type=adgeolocation&access_token=" + ACCESS_TOKEN;
//        }
//
//        model.addAttribute("facebookProfile", facebook.userOperations().getUserProfile());
//        PagedList<Post> feed = facebook.feedOperations().getFeed();
//        model.addAttribute("feed", feed);
//        return "hello";

        String urlCity = "https://graph.facebook.com/v2.9/search?q=kyiv&type=adgeolocation&location_types=['city']&access_token=" + ACCESS_TOKEN;

        String cities = restTemplate.getForObject(urlCity, String.class);
        System.out.println(cities);
        cities = cities.substring(8,cities.length()-1);

        try {
            JSONArray fbCities = new JSONArray(cities);

        for (int i = 0; i < fbCities.length(); i++) {
            JSONObject fbCity = fbCities.getJSONObject(i);
            String key = fbCity.getString("key");
            System.out.println(key);
            String name = fbCity.getString("name");
            System.out.println(name);
            String country_code = fbCity.getString("country_code");
            System.out.println(country_code);
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(new File("C:\\Users\\Vlad\\IdeaProjects\\facebookapi\\src\\main\\resources\\application.properties").getAbsolutePath());
        String urlCountry = "https://graph.facebook.com/v2.9/search?q=kyiv&type=adgeolocation&location_types=['country']&access_token=" + ACCESS_TOKEN;

//        ResponseEntity<Place[]> places = restTemplate.getForEntity(cities, Place[].class);
//        System.out.println(places);
        return "hello";
    }

    @RequestMapping(value = "{countryName}/{cityName}/{place}", method = RequestMethod.GET)
    public String getSearchRequest(Model model, @PathVariable String countryName, @PathVariable String cityName, @PathVariable String place) {
        String allPlacesByName = getAllSearchedPlaces(place);
        return getPlacesInLocation(allPlacesByName, countryName, cityName);
    }

    private String getPlacesInLocation(String allPlacesByName, String countryName, String cityName) {
        return null;
    }

    // $placeName can be partial name
    private String getAllSearchedPlaces(String placeName) {
        String urlPlacesByName = "https://graph.facebook.com/v2.9/search?q=egn&type=place&fields=id,name,location{city,city_id,country,country_code,latitude,longitude}" + ACCESS_TOKEN;
        return null;
    }

    public void displayHeaderInfo(@RequestHeader("Accept-Encoding") String encoding,
                                  @RequestHeader("Keep-Alive") long keepAlive)  {

    }

}