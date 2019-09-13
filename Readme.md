# GHBrowser

GHBrowser is a simple Android application written in Java, whose purpose is to search and display basic information on GitHub repositories.

  - Browse all public repositories
  - Search for a repository with keywords
  - Display advanced informations on a given repository.

## Features

- At startup, the application queries a list of all public repositories as provided by GitHub public API.
The result being a very long scrollable list, it uses pagination .
Note that currently pagination is implemented in the API query but not in the interface. This feature would be implemented using the "Infinite Scroll" pattern.

- The user has the possibility to search for a particular repo using a search view. When validating the query, the app retrieves a list of corresponding repositories. Again, pagination is not implemented in the view.

- In order to save network bandwith and optimise the user's experience, an existing list of repositories will be persisted across configuration changes.

- Clicking a repo will open a new Activity to show the repository details. The complete description of a repo is downloaded at this time.

- Details are split using a ViewPager, to switch between views by dragging the current view.
Only the first View is currently implemented.

- In order to save network bandwith and optimise the user's experience, the complete information of the repository is persisted across configuration changes.

## Tech
### Development
- this app is developed using the MVP pattern along the views.
- The API calls are made asynchronously.
- Information between activities are passed with the use of Parcelable objects in the intent.
- Android adapters are used to display a RecyclerView or a ViewPager
- UI uses material design patterns.
- ConstraintLayouts are used to create the UI views
- Internationalisation is ready through standard Android mechanisms
- The development is made with clean code practices in mind. The classes and members name should be easy to understand, thus minimizing the use of comments. Note that a good understanding of Android development and lifecycle is required.

### GIT
- this app's repository is very simple.
- it still relies on a Gitflow pattern

### Tests
- The code is unit tested, especially regarding the REST API calls and their effect on the interface
- a Jacoco gradle task is provided 'JacocoTestReport', which generated reports on code coverage. This prepares a Sonarqube integration, however that task is not currently provided

### Publication
- the app uses only the default flavor
- the publication signature key is provided at the root of the project. the credentials are: test/testme
- the gradle task is provided in order to make the generation of a production app automatic (via Jenkins)

### Dependencies
GHBrowser uses a number of external dependencies to work properly:

* AndroidX - The android framework for working with compatibility libraries: AppCompat, ConstraintLayout, RecyclerView, CardView but also for testing purpose: TestRunner, FragmentTesting
* Retrofit - To perform Rest queries
* GSON - A JSON to Object mapper
* Glide - Lib taking care of displaying and caching images
* Robolectric - The test framework for Android mocks
* Mockito - A mocking framework for unit tests
* Jacoco - Creates reports for code coverage for Java projects


License
----

MIT

