# Ibis
Note taking app with real time sync built with Compose

<img src="https://user-images.githubusercontent.com/6247940/113428617-62d04480-93d7-11eb-8af2-8fd1f7da55b9.png">

<img src="https://user-images.githubusercontent.com/6247940/113428615-619f1780-93d7-11eb-85a3-c83d1db379a6.png">


# Checklist
- [x] Notes UI with real time sync and offline capabilities
- [ ] Full text search
- [ ] Markdown support

## Architecture

This app focuses on scalable, flexible and reactive app architecure. Parts of the architecture are inspired by Chris Banes' [Tivi app](https://github.com/chrisbanes/tivi).

It is a version of MVVM with interactors as an additional layer to enhance re-usability. The app uses following frameworks


* [Jetpack compose](https://developer.android.com/jetpack/compose) 
* [Room](https://developer.android.com/topic/Libs/architecture/room)
* [Dagger Hilt](https://dagger.dev/hilt/)
* [ExoPlayer](https://github.com/google/ExoPlayer) - For audio playback
* Retrofit2, LiveData, Coroutines... a lot more


![final-architecture](https://user-images.githubusercontent.com/6247940/75632907-cb5f5780-5c00-11ea-974d-ff7a5e8b0a21.png)
