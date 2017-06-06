package com.egnyte.facebookapi.controller;

import com.egnyte.facebookapi.SearchedPlace;
import com.google.gson.*;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
@PropertySource("application.properties")
public class HelloController {

    private String TAG = HelloController.class.getSimpleName();

    private static final String BASE_URL = "https://graph.facebook.com/v2.9/";
    //    @Value("${spring.social.facebook.app-id}")
    private String appId = "462724750743444";
    //    @Value("${spring.social.facebook.app-secret}")
    private String appSecret = "e6ddc6ab45d201c143ef677ba7285da4";
    private final String ACCESS_TOKEN = appId + "|" + appSecret;

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

        String urlCity = BASE_URL + "search?q=Poznan&type=adgeolocation&location_types=['city']&access_token=" + ACCESS_TOKEN;
        RestTemplate restTemplate = new RestTemplate();
        String citiesJson = restTemplate.getForObject(urlCity, String.class);
        System.out.println(citiesJson);
        return "hello";
    }

    @RequestMapping(value = "{countryName}/{cityName}/{place}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getSearchRequest(Model model, @PathVariable String countryName, @PathVariable String cityName, @PathVariable String place) {
        System.out.println("in the g/f/d");
        List<SearchedPlace> allPlacesByName = getAllSearchedPlaces(place);
        System.out.println("got all places");
        List<SearchedPlace> placesInLocation = getPlacesInLocation(allPlacesByName, countryName, cityName);
        System.out.println("got filtered places");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        System.out.println("gson.toJson(placesInLocation)="+gson.toJson(placesInLocation));
        if (placesInLocation.isEmpty()){
            return "null";
        } else {
            return gson.toJson(placesInLocation);
        }
    }

    // $placeName can be partial name
    private List<SearchedPlace> getAllSearchedPlaces(String placeName) {
        String urlPlacesByName = BASE_URL + "search?q=" + placeName + "&type=place&fields=id,name,location{city,city_id,country,country_code,latitude,longitude}&access_token=" + ACCESS_TOKEN;
        System.out.println(urlPlacesByName);

        String nextUrl = urlPlacesByName;
        List<SearchedPlace> searchedPlaces = new ArrayList<>();
        boolean isLastJsonPage = false;
        while (!isLastJsonPage) {

            try {
                String jsonInput = readUrl(nextUrl);

                JsonParser parser = new JsonParser();
                JsonObject jsonRaw = parser.parse(jsonInput).getAsJsonObject();

                System.out.println(jsonRaw);
                JsonArray data = jsonRaw.get("data").getAsJsonArray();

                for (JsonElement dataElem : data) {
                    try {
                        JsonObject j = parser.parse(dataElem.toString()).getAsJsonObject();
                        String id = j.get("id").getAsString();
                        String name = j.get("name").getAsString();
                        System.out.println("blabla="+name);
                        JsonObject location = j.get("location").getAsJsonObject();
                        String city = null;
                        String city_id = null;
                        String country = null;
                        String country_code = null;
                        //TODO: is there a support of this feature?
                        try {
                            city = location.get("city").getAsString();
                        } catch (NullPointerException e) {
                            System.out.println("no city for " + name);
                        }
                        try {
                            city_id = location.get("city_id").getAsString();
                        } catch (NullPointerException e) {
                            System.out.println("no city_id for " + name);
                        }
                        try {
                            country = location.get("country").getAsString();
                        } catch (NullPointerException e) {
                            System.out.println("no country for " + name);
                        }
                        try {
                            country_code = location.get("country_code").getAsString();
                        } catch (NullPointerException e) {
                            System.out.println("no country_id for " + name);
                        }
                        Double latitude = location.get("latitude").getAsDouble();
                        Double longitude = location.get("longitude").getAsDouble();
                        SearchedPlace searchedPlace = new SearchedPlace();
                        searchedPlace.setId(id);
                        searchedPlace.setName(name);
                        searchedPlace.setCity(city);
                        searchedPlace.setCityId(city_id);
                        searchedPlace.setCountry(country);
                        searchedPlace.setCountryCode(country_code);
                        searchedPlace.setLatitude(latitude);
                        searchedPlace.setLongitude(longitude);

//                searchedPlace.setId(jElem.get("city").toString());
                        searchedPlaces.add(searchedPlace);
                        System.out.println(searchedPlace);
                    } catch (NullPointerException e) {
                        System.out.println("NPE: not enough data for current element");
                    }
                }

                try {
                    JsonObject paging = jsonRaw.get("paging").getAsJsonObject();
                    nextUrl = paging.get("next").getAsString();
                    isLastJsonPage = false;
                } catch (NullPointerException e) {
                    System.out.println("It is the last page of places from facebook api");
                    isLastJsonPage = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } //json read while ends here
//        for (
//                SearchedPlace jaData : searchedPlaces)
//
//        {
//            System.out.println(gson.toJson(jaData));
//        }
//        System.out.println(searchedPlaces.size());
        return searchedPlaces;
    }

    private List<SearchedPlace> getPlacesInLocation(List<SearchedPlace> allPlacesByName, String countryName, String cityName) {
//        String urlCity = String.format("%ssearch?q=Poznan&type=adgeolocation&location_types=['city']&access_token=" + ACCESS_TOKEN, BASE_URL);
//        System.out.println("\nurlCity:" + urlCity);
//        String urlCountry = BASE_URL + "search?q=Poland&type=adgeolocation&location_types=['country']&access_token=" + ACCESS_TOKEN;
        List<SearchedPlace> placesInLocation = new ArrayList<>();
        for (SearchedPlace searchedPlace : allPlacesByName) {
            try {
                boolean isCountryNameMatch;
                if (countryName.toLowerCase().equals("anycountry")){ //just feature
                    isCountryNameMatch = true;
                } else {
                    isCountryNameMatch = searchedPlace.getCountry().toLowerCase().equals(countryName.toLowerCase());
                    System.out.println(searchedPlace.getCountry().toLowerCase() + " and " + countryName.toLowerCase() + " is equal " + isCountryNameMatch);
                }

                boolean isCityNameMatch;
                if (cityName.toLowerCase().equals("anycity")){ //just feature
                    isCityNameMatch = true;
                } else {
                    isCityNameMatch = searchedPlace.getCity().toLowerCase().equals(cityName.toLowerCase());
                    System.out.println(searchedPlace.getCity().toLowerCase() + " and " + cityName.toLowerCase() + " is equal " + isCityNameMatch);
                }

                if (isCountryNameMatch && isCityNameMatch) {
                    placesInLocation.add(searchedPlace);
                    System.out.println("added " + searchedPlace.getName());
                }
            } catch (NullPointerException e) {
                System.out.println("no country or city in place named " + searchedPlace.getName());
            }
        }

        return placesInLocation;
    }

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder buffer = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    public void displayHeaderInfo(@RequestHeader("Accept-Encoding") String encoding,
                                  @RequestHeader("Keep-Alive") long keepAlive) {
    }

}