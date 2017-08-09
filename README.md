# Facebook API consumer
Facebook API consumer if started on server localhost:8080 can return places by sending request on localhost:8080/{country}/{city}/{place_name}
For example: localhost:8080/Ukraine/Kyiv/Luxoft

Also country can be replaced by any_counrty to ignore country, same with city - you can put any_city.
For example localhost:8080/any_counrty/any_city/Luxoft

The limitation of facebook api doesn't allow to get more than around 500 results so if you put 'cafe' as a place it will find cafes around USA, but no more (all other cafes won't get to the result screen).

Main structure of link used to get all results is:
https://graph.facebook.com/{version}/search?q={place_name}&type=place&fields=id,name,location{city,city_id,country,country_code,latitude,longitude}&access_token={my_app_id|my_app_secret}
app_id and app_secret are given on facebook after registering as a developer on their site.
All results received by sending request on the link above are filtered by {country} and {city}. 

*Thoughts in addition*
Unfortunately, there is no other possibility to filter places by city and country in facebook api request. You can try using "find places next to me", but facebook doesn't provide coordinates of cities - you have to get them by yourself. Facebook has become user oriented, so it can't be used so easily for anything you want.
