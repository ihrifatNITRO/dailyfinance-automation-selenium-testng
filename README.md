# Daily Finance Selenium Test Automation

Java-based UI automation for the Daily Finance web application. The project uses Selenium WebDriver with TestNG and Gradle. Its test suite covers registration, login, user-page, and adding-cost flows against the configured application site.

## Requirements

- JDK compatible with Gradle 8.14.6 (TODO: specify and pin the supported Java version).
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

The test setup opens `https://dailyfinance.roadtocareer.net/` in Chrome. Tests interact with this live site, so they require it to be reachable and the test account/data to be valid. TODO: make the base URL and test account configurable before using this suite in other environments.

## Gradle tasks

| Task | Description |
| --- | --- |
| `test` | Run the TestNG UI test suite. |
| `build` | Compile and run the project’s verification tasks, including tests. |
| `clean` | Remove Gradle build outputs. |

There are no separate npm-style scripts; use the Gradle Wrapper tasks above. The project applies Gradle's Java plugin and declares Selenium Java 4.46.0 and TestNG 7.10.2.

## Tests

Tests are under `src/test/java/tests` and use TestNG `@Test` methods. Run all tests with `test`, or target a test class with Gradle's `--tests` option, for example:

```powershell
.\gradlew.bat test --tests tests.LoginTest
```

The browser is started by `config.SetUp`. Note: teardown is currently not enabled (`@AfterTest` is commented out), so browser sessions may remain open after tests. Some tests contain fixed account details; do not treat these as secrets or reuse them outside the intended test environment. TODO: replace them with externally supplied test credentials and enable reliable browser cleanup.

## Environment variables

No application environment variables are currently read by the project. The application URL, Chrome browser choice, and account details are currently hard-coded in test code. TODO: introduce and document environment variables for the base URL and test credentials.

## Project structure

```text
.
├── build.gradle                 # Java plugin and Selenium/TestNG dependencies
├── settings.gradle              # Gradle project settings
├── gradlew / gradlew.bat        # Gradle Wrapper launchers
├── gradle/wrapper/              # Wrapper configuration and JAR
└── src
    ├── main/java/org/example/   # Sample Main entry point
    └── test/java
        ├── config/              # WebDriver setup
        ├── pages/               # Page objects for application screens
        └── tests/               # Registration, login, user, and cost tests
```

`org.example.Main` is a sample console entry point; normal project verification is performed through Gradle's `test` task.

## License

TODO: No license file or license declaration was found. Add a `LICENSE` file and replace this section with the selected license before distributing the project.