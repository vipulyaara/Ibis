# Ibis
[In Progress] Note taking app with real time sync built with Compose

<img src="https://user-images.githubusercontent.com/6247940/121817576-7ecb4e80-cc82-11eb-863f-eccbae4f092c.png">

<img src="https://user-images.githubusercontent.com/6247940/113428615-619f1780-93d7-11eb-85a3-c83d1db379a6.png">


# Checklist
- [x] Notes UI with real time sync and offline capabilities
- [ ] Full text search
- [ ] Markdown support

## Architecture

This app focuses on scalable, flexible and reactive app architecure. Parts of the architecture are inspired by Chris Banes' [Tivi app](https://github.com/chrisbanes/tivi).

It is a version of MVVM with interactors as an additional layer to enhance re-usability. The app uses following frameworks


* [Jetpack compose](https://developer.android.com/jetpack/compose) 
* [Firestore](https://firebase.google.com/docs/firestore)
* [Room](https://developer.android.com/topic/Libs/architecture/room) (deprecated; using Firestore instead)
* [Dagger Hilt](https://dagger.dev/hilt/)
* Retrofit2, LiveData, Coroutines... a lot more


![final-architecture](https://user-images.githubusercontent.com/6247940/75632907-cb5f5780-5c00-11ea-974d-ff7a5e8b0a21.png)
