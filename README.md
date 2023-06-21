Your task is to create a simple news reader application. The app will contain two screens. The first
will be a list of news article headlines, tapping on one of those headlines will open up the full
news story.

The project provides you with a mock API layer (find this in the `data.api` package). This API layer
contains two methods, `getNewsHeadlines` which will return a list of news headlines,
and `getNewsStory` which takes an ID for a news headline, and returns the full article.

Within the `data.api` package you will see multiple implementations of this API layer, one
implemented using Kotlin coroutines, one with RxJava3, and a final implementation using callbacks.
It is up to you which of these implementations you use depending on your experience level, but our
preference would be for you to use Kotlin Coroutines.

There are also two options for the UI Layer, either Jetpack Compose or Fragments with the view
system. Again it is up to you which you choose, but our preference would be Jetpack Compose. You may
have to change the AndroidManifest.xml file to point to the new Activity, depending on your choice.

You may wish to delete any packages you are not using.

You may use whatever external resources or websites you like to help you, and feel free to import
3rd party libraries where you see an opportunity to do so. You can also create, modify or delete any
classes and files within the project that you want to, you're not restricted to only working within
the given files, they should only be used as a guide/quick start point. The only restriction here is
please do not modify the files within the `api` package. Feel free to take a look at how they work
though!

Please spend no more than 1.5 hours on this test. We are not expecting you to finish every task, but
please be prepared to discuss how you would approach any non-complete tasks.

When you have finished, please ZIP up the project folder and either email it back to us, or upload
it somewhere for us to download.

Your tasks are as follows, we recommend you read them all in full before you start the first task.
Good luck!

# Task 1

Implement the Headlines Screen. This screen should display a scrollable list of headlines to the
user. Each item should contain the headline title, and the author's name. The headlines should be
populated from the API layer on screen load.

The screen should also have a loading state, and an error state. The error state can be tested by
changing `HAS_CONNECTION` to false in `Constants.kt`. You DO NOT need to implement any refreshing
behaviour or implement any retry on error.

It is up to you to define the path the data takes on it's way to the UI. The UI design of the app is
not important, as long as the content is readable regardless of screen size.

# Task 2

Implement the News Screen. The news screen should show the title, author, content, and the formatted
published at date.

Again, this screen should have a loading state and an error state.

# Task 3

Implement dependency injection with Hilt, it is left up to you to decide what is injected and how.
The project is set up with the Hilt dependencies already.

# Task 4

Implement a Room database to make the app work while offline. Both headlines and stories should be
stored in the database. The app should use the database as the source of truth. The app should
refresh the headlines and stories in the database whenever the headlines screen and individual
stories screens are shown, respectively.

Offline mode can be tested by changing `HAS_CONNECTION` to false in `Constants.kt`

The dependencies for Room are already set up in the project.
