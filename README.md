# Demo Flickr search image app
A demo Android application using Flickr API.
The user opens the app and presses the start button. After that the user puts their phone into their pocket and starts walking. 
The app requests a photo from the public flickr photo search api for his location every 100 meters to add to the stream. 
New pictures are added on top. Whenever the user takes a look at their phone, they see the most recent picture and can scroll through a stream of pictures which shows where the user has been. 
It should work for at least a two-hour walk.

## Documentation

### Libraries used in this app:
- For Network and serialization -> Retrofit, okhttp3, Moshi

- For UI -> Jetpack Compose

- For dependency injection -> Hilt

- For Media -> Landscapist

- For Unit Tests -> Junit, MockitoKotlin

- For Logs -> Timber


### Architecture used in this app:
The whole project is modularized and based on clean architecture pattern.

For the presentation layer, MVVM architecture is selected.

### Location update
A Foreground service is responsible for requesting location updates every 100 meters. In this case, the service requests new photo for user's new location.
We used a foreground service because we want location updates to run very frequently, in contrast with background service where Android operating systems forces some limitations.

### Presentation layer
As far as UI is concerned, our MainActivity hosts composable views.

When user presses Start button our foreground service starts fetching images, by calling SearchPhotosUseCase. The home screen viewModel collects every new api response from the usecase and updates the view.