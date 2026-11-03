# Daily Finance — End-to-End Test Automation Framework

[![Java](https://img.shields.io/badge/Java-11%2B-ED8B00?logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Gradle](https://img.shields.io/badge/Gradle-8.14.6-02303A?logo=gradle&logoColor=white)](https://gradle.org/)
[![Selenium WebDriver](https://img.shields.io/badge/Selenium%20WebDriver-4.46.0-43B02A?logo=selenium&logoColor=white)](https://www.selenium.dev/)
[![TestNG](https://img.shields.io/badge/TestNG-7.10.2-FF7043?logo=testng&logoColor=white)](https://testng.org/)
[![Java Faker](https://img.shields.io/badge/JavaFaker-1.0.2-blue)](https://github.com/DiUS/java-faker)
[![Design Pattern](https://img.shields.io/badge/Design%20Pattern-Page%20Object%20Model%20(POM)-brightgreen)](#architecture--design-pattern)

A robust, scalable UI test automation framework for the **Daily Finance** web application ([https://dailyfinance.roadtocareer.net/](https://dailyfinance.roadtocareer.net/)). Built using **Java**, **Selenium WebDriver**, and **TestNG** with **Gradle**, following enterprise-grade **Page Object Model (POM)** principles, dynamic data generation, session persistence, and end-to-end integration workflows.

---

## Table of Contents

- [Architecture & Design Pattern](#architecture--design-pattern)
- [End-to-End Test Suite Sequence](#end-to-end-test-suite-sequence)
  - [Execution Flow Diagram](#execution-flow-diagram)
  - [Detailed Test Sequence & Scenarios](#detailed-test-sequence--scenarios)
- [Test Data & State Management](#test-data--state-management)
- [Tech Stack & Dependencies](#tech-stack--dependencies)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Running Tests](#running-tests)
- [Project Structure](#project-structure)
- [Reporting & Artifacts](#reporting--artifacts)

---

## Architecture & Design Pattern

The framework is architected for high maintainability, reusability, and readability:

- **Page Object Model (POM):** Clean separation between page web element locators (`@FindBy`), user interactions/actions in `pages/`, and validation assertions in `tests/`.
- **PageFactory Initialization:** Clean UI element binding via `PageFactory.initElements(driver, this)`.
- **Dynamic Synthetic Data Generation:** Leverages `JavaFaker` to generate randomized, realistic user and cost data per execution to ensure test idempotency and prevent duplicate key collisions.
- **Session & Token Management:** Demonstrates automated extraction of JWT/Auth tokens from browser `localStorage` and programmatically injecting them across subsequent test classes to streamline authenticated workflows.
- **Data Persistence:** Synchronizes dynamically created test users and tokens into structured JSON/text files (`Users.json`, `AuthToken.txt`) for cross-test dependency chains.

---

## End-to-End Test Suite Sequence

The test suite executes an end-to-end user lifecycle test workflow configured via TestNG suite configuration (`src/test/resources/suite.xml`).

### Execution Flow Diagram

```text
+-----------------------------------------------------------------------------------+
|                            E2E Automation Workflow                                |
+-----------------------------------------------------------------------------------+
                                          |
                                          v
                   +---------------------------------------------+
                   |  1. User Registration (RegistrationTest)    |
                   |  - Negative: Reject non-Gmail domains       |
                   |  - Positive: Register valid user & persist  |
                   |              credentials to Users.json      |
                   +---------------------------------------------+
                                          |
                                          v
                   +---------------------------------------------+
                   |  2. User Authentication (LoginTest)         |
                   |  - Negative: Validate invalid login error   |
                   |  - Positive: Log in with latest user data   |
                   |  - Extract authToken from localStorage      |
                   |    and save to AuthToken.txt                |
                   +---------------------------------------------+
                                          |
                                          v
                   +---------------------------------------------+
                   |  3. Expense Management (AddCostTest)        |
                   |  - Inject authToken into localStorage       |
                   |  - Test 1: Add cost with full dataset       |
                   |  - Test 2: Add cost with mandatory fields   |
                   +---------------------------------------------+
                                          |
                                          v
                   +---------------------------------------------+
                   |  4. Profile Update (ProfileUpdateTest)      |
                   |  - Authenticate existing user session       |
                   |  - Update account email to a new address    |
                   |  - Sync updated email back to Users.json    |
                   +---------------------------------------------+
```

---

### Detailed Test Sequence & Scenarios

#### 1. Phase 1 — User Registration (`RegistrationTest`)
*Suite Tag: `User Registration`*

| Priority | Test Method | Scenario Type | Description & Validation |
| :---: | :--- | :---: | :--- |
| **P1** | `deRegInvalid()` | Negative | Submits registration with a non-Gmail address (`@yahoo.com`). Verifies that the application prevents submission and renders the error toast message: `"Only Gmail addresses are accepted."`. |
| **P2** | `doRegValid()` | Positive (E2E) | Generates random user details (first name, last name, unique Gmail address, secure password, phone number, address, gender) using `Utils`/`JavaFaker`. Accepts terms and submits registration. Asserts `"registered successfully"` toast notification and appends the new user record into `src/test/resources/Users.json`. |

#### 2. Phase 2 — User Authentication (`LoginTest`)
*Suite Tag: `User Login`*

| Priority | Test Method | Scenario Type | Description & Validation |
| :---: | :--- | :---: | :--- |
| **P1** | `doLoginInvalid()` | Negative | Attempts to log in using an unregistered email address along with the password of the newly registered user. Asserts the invalid login feedback message `"Invalid email or password"`. |
| **P1** | `doLoginValid()` | Positive (E2E) | Retrieves the latest registered user credentials from `Users.json` and performs login. Asserts successful navigation to the dashboard by validating header `"User Daily Costs"`. Extracts the `authToken` directly from browser `localStorage` using `JavascriptExecutor` and stores it into `src/test/resources/AuthToken.txt`. |

#### 3. Phase 3 — Expense Management (`AddCostTest`)
*Suite Tag: `Add New Item`*

| Priority | Test Method | Scenario Type | Description & Validation |
| :---: | :--- | :---: | :--- |
| **Pre-Hook** | `@BeforeMethod getAuthToken()` | Precondition | Reads the stored token from `AuthToken.txt`, injects it into browser `localStorage`, and navigates directly to the `/add-cost` route. |
| **P1** | `doAddCostFull()` | Functional (Full) | Populates all available cost fields (Item Name, Amount, Purchase Date `ddMMyyyy`, Month dropdown, and Remarks) using dynamic Faker datasets. Submits the form and verifies the confirmation message: `"Product added successfully!"`. |
| **P2** | `doAddCostRequires()` | Functional (Mandatory) | Fills only mandatory fields (Item Name and Amount). Submits and asserts that optional fields default gracefully with confirmation: `"Product added successfully!"`. |

#### 4. Phase 4 — Profile & Account Management (`ProfileUpdateTest`)
*Suite Tag: `Profile Email Update`*

| Priority | Test Method | Scenario Type | Description & Validation |
| :---: | :--- | :---: | :--- |
| **Pre-Hook** | `@BeforeMethod login()` | Precondition | Loads latest credentials from `Users.json` and authenticates the user session into the web application. |
| **Default** | `changeEmail()` | Functional (E2E) | Navigates to user profile, updates email address to a newly generated unique UUID Gmail address (`abra<uuid>@gmail.com`), submits update, and asserts toast: `"User updated successfully!"`. Synchronizes the modified email back into `Users.json` to keep test data coherent. |

*(Optional / Standalone: `UserTest.clickAddCost()` tests dashboard navigation from `/user` to the "Add New Cost" screen).*

---

## Test Data & State Management

| Resource File | Purpose & Role in Test Pipeline |
| :--- | :--- |
| `src/test/resources/Users.json` | Centralized JSON store for registered test users. New accounts are written here during registration and updated during profile changes. |
| `src/test/resources/AuthToken.txt` | Stores the active session `authToken` extracted from `localStorage` during login, enabling token-based fast authentication in subsequent tests. |
| `src/test/resources/suite.xml` | TestNG XML suite descriptor configuring the sequential end-to-end execution order. |

---

## Tech Stack & Dependencies

- **Language:** Java (JDK 11+)
- **Build Tool:** Gradle 8.14.6 (Gradle Wrapper included)
- **Automation Framework:** Selenium WebDriver 4.46.0
- **Test Runner & Assertions:** TestNG 7.10.2
- **Test Data Generation:** JavaFaker 1.0.2
- **JSON Processing:** `org.json:json:20240303`
- **Browser:** Google Chrome (managed via Selenium Manager / ChromeDriver)

---

## Prerequisites

- **Java Development Kit (JDK):** Version 11 or higher installed and configured on your `PATH`.
- **Google Chrome:** Installed on host machine (Selenium Manager resolves compatible driver binaries automatically).
- **Network Access:** Stable internet connection to access the live application under test (`https://dailyfinance.roadtocareer.net/`).

---

## Installation & Setup

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd dailyfinance-automation-selenium-testng
   ```

2. **Verify Gradle environment:**
   - **Windows:**
     ```powershell
     .\gradlew.bat --version
     ```
   - **macOS / Linux:**
     ```bash
     ./gradlew --version
     ```

---

## Running Tests

### 1. Run Complete End-to-End Test Suite

Executes the full TestNG test suite configured in `src/test/resources/suite.xml`:

- **Windows (PowerShell / CMD):**
  ```powershell
  .\gradlew.bat test
  ```

- **macOS / Linux:**
  ```bash
  ./gradlew test
  ```

### 2. Run Specific Test Class

To target individual test classes (e.g., during test development or debugging):

```powershell
# Run Registration tests only
.\gradlew.bat test --tests "tests.RegistrationTest"

# Run Login tests only
.\gradlew.bat test --tests "tests.LoginTest"

# Run Cost Management tests only
.\gradlew.bat test --tests "tests.AddCostTest"

# Run Profile Update tests only
.\gradlew.bat test --tests "tests.ProfileUpdateTest"
```

### 3. Build & Compile without Running Tests

```powershell
.\gradlew.bat build -x test
```

### 4. Clean Build Directory

```powershell
.\gradlew.bat clean
```

---

## Project Structure

```text
dailyfinance-automation-selenium-testng
├── build.gradle                          # Build configuration, plugins & dependencies
├── settings.gradle                       # Gradle project settings
├── gradlew / gradlew.bat                 # Cross-platform Gradle Wrapper executables
├── gradle/wrapper/                       # Gradle wrapper configuration
└── src
    ├── main/java/org/example/            # Main source root
    └── test
        ├── java
        │   ├── config/                   # Base test setup & WebDriver configuration
        │   │   └── SetUp.java            # Browser lifecycle management (@BeforeMethod / @AfterMethod)
        │   ├── pages/                    # Page Object Model classes
        │   │   ├── AddCostPage.java      # Add Cost form elements and actions
        │   │   ├── LoginPage.java        # Authentication form elements and actions
        │   │   ├── ProfileUpdatePage.java# Profile management elements and actions
        │   │   ├── RegistrationPage.java # User registration elements and actions
        │   │   └── UserPage.java         # User dashboard interactions
        │   ├── tests/                    # TestNG test classes (E2E test scenarios)
        │   │   ├── AddCostTest.java      # Expense creation validations
        │   │   ├── LoginTest.java        # Login positive and negative scenarios
        │   │   ├── ProfileUpdateTest.java# Profile updates & email change validations
        │   │   ├── RegistrationTest.java # Registration positive and negative scenarios
        │   │   └── UserTest.java         # Dashboard navigation checks
        │   └── utils/                    # Test utility classes
        │       └── Utils.java            # Dynamic data generators via JavaFaker
        └── resources/                    # Test assets and configuration
            ├── AuthToken.txt             # Saved session token for stateful test flows
            ├── Users.json                # User credential store
            └── suite.xml                 # TestNG suite descriptor for E2E sequence
```

---

## Reporting & Artifacts

Test execution reports are automatically generated by Gradle and TestNG upon test completion:

- **Gradle HTML Test Report:**
  ```text
  build/reports/tests/test/index.html
  ```
- **TestNG Native Output:**
  ```text
  build/reports/tests/test/testng-results.xml
  ```

Open the `index.html` report in any standard browser to review detailed test execution results, pass/fail status, execution duration, and stack traces for any assertion failures.
