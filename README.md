# Helmetly - Android application

<p align="center">
  <img src="https://github.com/raghulk/Helmetly/blob/master/app/src/main/ic_launcher-playstore.png" width="200" height="200">
</p>

- This repository contains code for a sports tracking app that used data obtained from the SportsDB API.

- This app provides users a way to manage their favorite teams from across the globe for any sport. You just have to specify a sport and a country to get to your team. 
You can then view those teams' past events, about them, latest news in the form of stories (like in Instagram). Multiple google users can use the app to sign to track 
different teams of their choice. Any suggestions, feedback or feature requests are welcome. You can do so by visiting the playstore.

- APIs used
1. https://www.thesportsdb.com/api.php
2. https://newsapi.org/ - for fetching latest news for all the favorite teams

- Play Store Link - https://play.google.com/store/apps/details?id=com.sportstracking.helmetly

- Link to the APK (Until the app is live on Play Store) - <a href="https://github.com/raghulk/Helmetly/raw/master/app/debug/Helmetly.apk" target="_blank">Download Helmetly</a>

- App screenshots are attached at the bottom of the readme.

## Libraries Used 
- Firebase Auth (@firebase) - For authentication purposes 
- Retrofit (@retrofit) - For data request purposes from api (json and bitmap)
- Moshi (@moshi) - to convert JSON returned into POJO objects
- Glide (@glide) - to load images from any service
- TinyDB (@tinydb) - For storing and retrieving objects as json strings in shared preferences as string set
- Circle Image View (@civ) - For displaying team logos and profile picture in circular image views
- App Intro (@appintro) - For displaying the application's walkthorugh
- Stories View (@storyview) - For displaying the news retrieved for each team into stories like Instagram

## Other Common Elements Used
- Splash Screen
- Recycler View
- Floating Action Button
- Spinner for dropdowns
- Search filter
- Card View
- Bottom Navigation 

## Design 
- Material design 

## Details about the application 
- The project has a main(home) activity which is the core of the application where any user would spend the most time. It has a events page where the user could see and change
the team for which the events are displayed. There is also a stories section on the top which loads latest news for the teams selected by the user from a news API to display 
it in the form of stories (like in Instagram). It also has a more (options) page where there is a user's profile image with their name, email address, and links relevant to 
the project and the developer.
- The app launches with a splash screen, and a walkthrough for first time users where they are next taken to a sign in page (Google Authentication based). The user is then asked
to pick atleast one favorite team to proceed to the main screen of the application.
- The main screen also has a favorite manager where any user can remove their previously selected teams from a horizontal scroll on the top of the screen. They can add teams 
by going through the pages picking the right sport and country of their team to add them as their favorite team. There is also an info button on the team selection page for 
every team to provide more information about that team.
- Unsplash images with keywords relating to the sport or team are used to retrieve images when not provided by the current API.
- Retrofit is used to load data from the API, and Moshi is used to convert them to Kotlin data class objects.
- MVVM pattern has been used wherever there was a need to store data across fragments, and to preserve them on configuration changes. 
- The code has been packaged into 5 main packages (ui, data, network, utility, ads). 

## Note 
For developers out there, if you wish to contribute to the project, you are very welcome to do so. 

## License
This project is licensed under the MIT License - see the LICENSE.md file for details

## Developer:
- @raghulk - https://github.com/raghulk | https://linkedin.com/in/raghul-krishnan

## App Screenshots:

- Here are the screenshots of the app arranged in chronological order for better understanding the UI and flow. 

<a href="https://github.com/raghulk/Helmetly/blob/master/screenshots/splash_screen.png"><img 
  src="https://github.com/raghulk/Helmetly/blob/master/screenshots/splash_screen.png" align="left" height="540" width="270"></a>

<a href="https://github.com/raghulk/Helmetly/blob/master/screenshots/walkthrough_1.png"><img 
  src="https://github.com/raghulk/Helmetly/blob/master/screenshots/walkthrough_1.png" align="left" height="540" width="270"></a>

<a href="https://github.com/raghulk/Helmetly/blob/master/screenshots/walkthrough_2.png"><img 
  src="https://github.com/raghulk/Helmetly/blob/master/screenshots/walkthrough_2.png" align="left" height="540" width="270"></a>

<a href="https://github.com/raghulk/Helmetly/blob/master/screenshots/walkthrough_3.png"><img 
  src="https://github.com/raghulk/Helmetly/blob/master/screenshots/walkthrough_3.png" align="left" height="540" width="270"></a>
  
<a href="https://github.com/raghulk/Helmetly/blob/master/screenshots/walkthrough_4.png"><img 
src="https://github.com/raghulk/Helmetly/blob/master/screenshots/walkthrough_4.png" align="left" height="540" width="270"></a>

<a href="https://github.com/raghulk/Helmetly/blob/master/screenshots/sign_in.png"><img 
src="https://github.com/raghulk/Helmetly/blob/master/screenshots/sign_in.png" align="left" height="540" width="270"></a>

<a href="https://github.com/raghulk/Helmetly/blob/master/screenshots/sport_selection.png"><img 
src="https://github.com/raghulk/Helmetly/blob/master/screenshots/sport_selection.png" align="left" height="540" width="270"></a>

<a href="https://github.com/raghulk/Helmetly/blob/master/screenshots/country_selection.png"><img 
src="https://github.com/raghulk/Helmetly/blob/master/screenshots/country_selection.png" align="left" height="540" width="270"></a>

<a href="https://github.com/raghulk/Helmetly/blob/master/screenshots/team_selection.png"><img 
src="https://github.com/raghulk/Helmetly/blob/master/screenshots/country_selection.png" align="left" height="540" width="270"></a>

<a href="https://github.com/raghulk/Helmetly/blob/master/screenshots/team_information1.png"><img 
src="https://github.com/raghulk/Helmetly/blob/master/screenshots/team_information1.png" align="left" height="540" width="270"></a>

<a href="https://github.com/raghulk/Helmetly/blob/master/screenshots/team_information2.png"><img 
src="https://github.com/raghulk/Helmetly/blob/master/screenshots/team_information2.png" align="left" height="540" width="270"></a>

<a href="https://github.com/raghulk/Helmetly/blob/master/screenshots/events1.png"><img 
src="https://github.com/raghulk/Helmetly/blob/master/screenshots/events1.png" align="left" height="540" width="270"></a>

<a href="https://github.com/raghulk/Helmetly/blob/master/screenshots/events2.png"><img 
src="https://github.com/raghulk/Helmetly/blob/master/screenshots/events2.png" align="left" height="540" width="270"></a>

<a href="https://github.com/raghulk/Helmetly/blob/master/screenshots/story_view.png"><img 
src="https://github.com/raghulk/Helmetly/blob/master/screenshots/story_view.png" align="left" height="540" width="270"></a>

<a href="https://github.com/raghulk/Helmetly/blob/master/screenshots/more_view.png"><img 
src="https://github.com/raghulk/Helmetly/blob/master/screenshots/more_view.png" align="left" height="540" width="270"></a>
