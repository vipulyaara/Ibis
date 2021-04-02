# Ibis
Note taking app with real time sync built with Compose

<img src="https://user-images.githubusercontent.com/6247940/110037150-4ee4e480-7d3e-11eb-9e53-8ea8e20d16a3.png">

<img src="https://user-images.githubusercontent.com/6247940/110037302-80f64680-7d3e-11eb-8729-43340808fd3c.png">


## Architecture

This app focuses on scalable, flexible and reactive app architecure. Parts of the architecture are inspired by Chris Banes' [Tivi app](https://github.com/chrisbanes/tivi).

It is a version of MVVM with interactors as an additional layer to enhance re-usability. The app uses following frameworks


* [Jetpack compose](https://developer.android.com/jetpack/compose) 
* [Room](https://developer.android.com/topic/Libs/architecture/room)
* [Dagger Hilt](https://dagger.dev/hilt/)
* [ExoPlayer](https://github.com/google/ExoPlayer) - For audio playback
* Retrofit2, LiveData, Coroutines... a lot more


![final-architecture](https://user-images.githubusercontent.com/6247940/75632907-cb5f5780-5c00-11ea-974d-ff7a5e8b0a21.png)
