## WeatherApp
Weather, everybody wants to know how it is going to be during the week. Will it be rainy, windy,
or sunny? Luckily for us, in the information age, there are open APIs to retrieve information
about it.

#### Information:
 * AndroidArchitectureComponents are used for the app as recommended architecture approach
   - Cities and Weather information is saved into local db with Room, so without internet connection user can access cached information
   - ViewModel and LiveData allows to implement orientation change without without having to reload the data
   
 * Google Places API is used for city selection
   - user can select a place on the map
   - pick one of the places nearby
   - search for the location by name
   
 * Pull to refresh implemented on weather screen, to manually update forecast information
 * Outdated information will be deleted from db automatically  
 * Resources and helpers for weather UI representation are used from Sunshine application
 
     
