## Copernicana
<i>Explore Earth and Space with the beautiful imagery provided by the Nasa APIs</i>

Copernicana is an app for exploring Earth and Space through the beautiful imagery provided by
the Nasa API and with the latest informations about astronauts in space and about the
International Space Station.<br>
Copernicana is my Capstone Project for the Android Developer Nanodegree by Udacity. <br>
The app is divided in three main sections: <br>
#### APOD SECTION <br>
 This section is dedicated to the Astronomy Picture of the Day (a.k.a APOD).<br>
 Every day Nasa chooses a significative astronomic picture commented by a professional astronomer (all the
images can also be seen at this website:​https://apod.nasa.gov/apod/astropix.html
The app shows the image of the day with the same description provided by Nasa and it allows
the user to save the image in a list of favorites. The user can search for a previous APOD for a
specific day in time and save it in favorites as well. The user can see all the previous APOD
saved previously in the database. API docs at: ​https://api.nasa.gov/api.html#apod <br>
<p align="center">
<img src="https://github.com/aleperf/Copernicana/blob/master/example_images/apod_detail.png" width="400"/></p>
<br>

#### EPIC SECTION <br>
 ( or The Blue Marble section), dedicated to images by the EPIC camera (Earth
Polychromatic Imaging Camera) on board of the DSCOVR. The images show the Earth seen from space, as in the following picture: <br>
<p align="center">
<img src="https://github.com/aleperf/Copernicana/blob/master/example_images/the_blue_marble_detail.png" width="400"/></p>
<br>
The app shows the latest images in natural and enhanced colors from the Epic camera. A
search section allows the user to search for Epic images for a specific day. Both images of the
day and searched images can be saved in a list of favorites.
API docs at: ​https://api.nasa.gov/api.html#EPIC​ and ​https://epic.gsfc.nasa.gov/about/api <br>

#### ISS SECTION <br>
A section dedicated to the International Space Station and to astronauts in space. The app
shows the latest position of the ISS.
The app lists the astronauts in space now and shows some information about them (pictures,
brief bios, wikipedia page).
API docs:​ http://open-notify.org/ </br>
<p align="center">
<img src="https://github.com/aleperf/Copernicana/blob/master/example_images/astronauts_land.png" width="400"/></p>
<br>

#### --- COMING SOON: MARS SECTION --> images from the Rovers on Mars --- <br>
#### TECHNICAL DETAILS
Copernicana needs 2 main API keys and to set up Firebase in order to work. <br>
1) You need a NASA API key that can be requested here: https://api.nasa.gov/index.html#apply-for-an-api-key in order to query the NASA Apis more the a few times per hour<br>
2) You also need an ANDROID API KEY: the app uses a standalone Youtube player (so even those rare devices without a youtube app can play the youtube
videos Nasa chooses sometimes instead of an image as  Astronomy Picture of the day), so you need also to download the Youtube API client, more info about here: https://developers.google.com/youtube/android/player/ </br>
3) Copernicana uses Firebase Storage as backend service for the app and Firebase Storage can be accessed only by authenticated users, so you also need to setup a firebase account and to include a google-services.json file with your credential in the app, more info about it here: https://firebase.google.com/docs/android/setup <br>
Once you have all the API keys and you know the name of your storage, create a file gradle.properties and store there 3 constants: <br>
NASA_API_KEY="REPLACE_THIS_WITH_YOUR_API_KEY" <br>
COPERNICANA_STORAGE="THE_URL_OF_YOUR_FIREBASE_STORAGE_HERE"
ANDROID_API_KEY="YOUR_ANDROID_API_KEY_HERE" <br>
The gradle.properties file is not included in this repository you need to create it yourself. <br>
Once you have a storage, create there a json file with some data about the astronauts and create a folder with the picture of the astronatus , I have included the json file in this repository in the folder example_json for guidance: the Astronaut class in the model package is based on this file.
Once you have done everything the app should compile and once authenticated you can access your firebase storage to download the data you have created: give yourself a pack on the shoulder, well done!
<br>
If everything works fine, give yourself a pat on the shoulder: well done! <br>

#### Libraries
Copernicana uses the following libraries: <br>
Dagger <br>
Room <br>
RxJava and RxAndroid <br>
Retrofit <br>
Firebase Authentication <br>
Firebase Storage <br>
Firebase UI <br>
Firebase Jobdispatcher <br>
Youtube library <br>
Glide <br>

#### Design Patterns and extras
The app uses a repository design pattern: AstroRepository is the only hub for downloading from network and for querying the database.<br>
The app also implements a widget for viewing the Astronomy Picture of the day
<br>
<br>
#### Credits
Images shown in app from the Nasa Apis are by Nasa or by their relative author where speicified.<br>
All other images are by Unsplash, all icons by Flaticon (see the files in the drawable folder for the specific credit to the artist).
