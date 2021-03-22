# KeepCompose

## Screenshots

<p>
  <img src="https://github.com/nikolaDrljaca/KeepCompose/blob/master/screenshots/Screenshot_1.png" width="30%" />
  <img src="https://github.com/nikolaDrljaca/KeepCompose/blob/master/screenshots/Screenshot_2.png" width="30%" />
  <img src="https://github.com/nikolaDrljaca/KeepCompose/blob/master/screenshots/Screenshot_3.png" width="30%" />
</p>

## About

**Design by:** [this person on dribbble](https://dribbble.com/shots/11875872-A-simple-and-lightweight-note-app).

None of the design behind the app is mine, some elements are inspired by it, but all the credits go to the person above.

The project was made to practice app building in the latest android UI framework [Jetpack Compose](https://developer.android.com/jetpack/compose).
After completing the [pathway](https://developer.android.com/courses/pathways/compose), I decided to build a note-taking application first, as you usually do. :smile:

Only the most essential features are implemented, so no settings screen, additional themeing, notifications or anything like that.
## Tech Stack

- [Jetpack Compose(ComposeUI)](https://developer.android.com/jetpack/compose): New UI toolkit for building android apps fully in Kotlin
- [Room](https://developer.android.com/training/data-storage/room): Android Architechture Component to manage database operations
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel): Android Architecture Component to store view-based data that survives configuration changes. Not necessary for Compose, but a good place to store business logic.
- [Compose-Navigation](https://developer.android.com/jetpack/compose/navigation): Jetpack Navigation artifact to allow navigation between composables
- [Kotlin Flows and StateFlows](https://developer.android.com/kotlin/flow): A Kotlin-Coroutines based stream data type

## Installation/Download
Clone this repository or download the zip file and import into **Android Studio**.

It's important to note that in order to work in Compose( * *which is in beta at the momemnt* * ) you must download the Canary version of android studio.

You can do that [here](https://developer.android.com/studio/preview).
