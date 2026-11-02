# Daily Finance Selenium Test Automation

[![Java](https://img.shields.io/badge/Java-version%20TODO-ED8B00?logo=openjdk&logoColor=white)](https://www.java.com/)
[![Gradle](https://img.shields.io/badge/Gradle-8.14.6-02303A?logo=gradle&logoColor=white)](https://gradle.org/)
[![Selenium](https://img.shields.io/badge/Selenium-4.46.0-43B02A?logo=selenium&logoColor=white)](https://www.selenium.dev/)
[![TestNG](https://img.shields.io/badge/TestNG-7.10.2-FF7043)](https://testng.org/)

Java-based UI automation for the Daily Finance web application. The project uses Selenium WebDriver and TestNG, with Gradle as its build and dependency manager. Tests cover registration, login, user-page, profile-update, and adding-cost flows against the configured application site. The `src/main` source set also contains a small sample console entry point; this is not the automation runner.

## Requirements

- A JDK compatible with Gradle 8.14.6 (the wrapper distribution is pinned in `gradle/wrapper/gradle-wrapper.properties`; TODO: specify the supported Java version).
- Google Chrome installed.
- Internet access on the first Gradle Wrapper run to download Gradle/dependencies and while running tests to access the target site.
- A compatible ChromeDriver. Selenium 4.46.0 uses Selenium Manager to locate/manage drivers when available; otherwise configure a compatible driver in the environment.

## Setup and run

The Gradle Wrapper is included, so a separate Gradle installation is not required.

```powershell
.\gradlew.bat test
```

On macOS or Linux:

```shell
./gradlew test
```

To compile without running tests:

```powershell
.\gradlew.bat build
```

The test setup opens `https://dailyfinance.roadtocareer.net/` in Chrome. Tests interact with this live site, so they require it to be reachable and any required account/data to be valid. The URL and test data are hard-coded or stored in test resources; TODO: make the target URL and test accounts configurable before using the suite in other environments.

## Gradle tasks

| Task | Description |
| --- | --- |
| `test` | Run Gradle's test task. **TODO:** configure `test { useTestNG() }` in `build.gradle`; TestNG is currently a dependency, but Gradle is not configured to discover TestNG tests. |
| `build` | Run the Java plugin's build lifecycle, including compilation and the configured test task. |
| `clean` | Remove Gradle build outputs. |

There are no separate npm-style scripts; use the Gradle Wrapper tasks above. The project applies Gradle's Java plugin and declares Selenium Java 4.46.0, TestNG 7.10.2, JavaFaker 1.0.2, and `org.json` 20240303.

## Tests

Tests are under `src/test/java/tests` and use TestNG `@Test` methods. After configuring the Gradle test task for TestNG (see above), run all tests with `test`, or target a test class with Gradle's `--tests` option, for example:

```powershell
.\gradlew.bat test --tests tests.LoginTest
```

The browser is started by `config.SetUp` using `ChromeDriver`; Selenium Manager may locate a compatible driver. Although a `tearDown` method exists, its `@AfterTest` annotation is commented out, so browser sessions may remain open. `AuthToken.txt` stores an authentication token consumed by the add-cost test, and registration writes account data (including passwords) to `Users.json`. Do not commit real credentials or tokens; remove them from version control and rotate any exposed credentials. TODO: source secrets safely and enable reliable browser cleanup.

## Environment variables

No application environment variables are currently read by the project. The application URL and Chrome browser choice are hard-coded, and test data is read from or written to files under `src/test/resources`. TODO: introduce and document environment variables for the base URL and test credentials.

## Project structure

```text
.
├── build.gradle                 # Java plugin and Selenium/TestNG dependencies
├── settings.gradle              # Gradle project settings
├── gradlew / gradlew.bat        # Gradle Wrapper launchers
├── gradle/wrapper/              # Wrapper configuration and JAR
└── src
    ├── main/java/org/example/   # Sample Main entry point
    └── test
        ├── java
        │   ├── config/          # WebDriver setup
        │   ├── pages/           # Page objects for application screens
        │   ├── tests/           # Registration, login, user, profile, and cost tests
        │   └── utils/           # Test data helpers
        └── resources/           # JSON test users and auth-token data
```

`org.example.Main` is a sample console entry point. The automation entry points are TestNG test classes in `src/test/java/tests`; Gradle's TestNG integration still needs configuration before those tests can be run through the Gradle `test` task.

## License

TODO: No license file or license declaration was found in the repository. Add a `LICENSE` file and replace this section with the selected license before distributing the project.