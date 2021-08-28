# ShoesIn

This App has been developed as part of the Udacity Android Kotlin Developer Nanodegree Course for the Exercise Project "Asteroid Radar". In this project I applyed my skills in applying diferents frameworks fetching data from the internet, saving data to a database, and display the data in a clear, compelling UI by building a Asteroid Radar app. 

Asteroid Radar is an app to view the asteroids detected by NASA that pass near Earth, you can view all the detected asteroids given a period of time with data such as the size, velocity, distance to earth and if they are potentially hazardous

The app is building with two screens:

* Asteroids list
* Asteroid detail

|Asteroids list|Asteroid detail|
|---|---|
|<img width="300" alt="Captura de Pantalla 2021-08-28 a la(s) 2 40 31" src="https://user-images.githubusercontent.com/37081739/131210544-7f550bc7-4d9d-4a47-8443-524f634a8982.png">|<img width="300" alt="Captura de Pantalla 2021-08-28 a la(s) 2 41 24" src="https://user-images.githubusercontent.com/37081739/131210563-fc45b912-a36f-4ff0-a544-fe486d86cf12.png">|

---

## App 
Designed for Phones and NOT for Tablets

---

## Rubric followed for the Project

### RecyclerView

* Create and reuse views in an Android app using RecyclerView.
	* The app displays a list of asteroids in the Main Screen by using a RecyclerView, when tapping an item the app opens Details screen.

### Network
      
* Build an application that connects to an internet server to retrieve and display live data.
	* The asteroids displayed in the screens are downloaded from the NASA AP.

* Use networking and image best practices to fetch data and images.
	* The NASA image of the day is displayed in the Main Screen.

### Database and Caching

* Create a database to store and access user data over time.
	* The app can save the downloaded asteroids in the database and then display them also from the database. The app filters asteroids from the past.

* Implement offline caching to allow users to interact with online content offline.
  * The app downloads the next 7 days asteroids and saves them in the database once a day using workManager with requirements of internet connection and device plugged in. The app can display saved asteroids from the database even if internet connection is not available.    

### Accessibility

* Add talkback and push-button navigation to make an Android app accessible.
	* The app works correctly in talk back mode, it provides descriptions for all the texts and images: Asteroid images in details screen and image of the day. It also provides description for the details screen help button.

---

## Icon and Image credits

* App Icon is from: [Free design file](https://freedesignfile.com/).
* Other images: [freepng](https://www.freepng.es/) and [pngwing](www.pngwing.com).
