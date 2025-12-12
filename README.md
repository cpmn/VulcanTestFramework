<!--
VulcanTestFramework
A QA Automation Project by Claudia Paola Muñoz (cpmn.tech)
Copyright (c) 2025
MIT License
-->

# 🖖 VulcanTestFramework  
**A logical, scalable, and modular UI + API Automation Framework built with Java 21, Selenium WebDriver, Cucumber (BDD), RestAssured, and Gradle.**

> *“Logic is the beginning of wisdom, not the end.” — Spock*  
Inspired by Vulcan principles of clarity, precision, and efficiency.

---

## 🌌 Overview

**VulcanTestFramework** is a professional-grade automation framework designed for **UI and API testing** under a unified architecture.  
It implements industry best practices: Page Object Model (POM), BDD, layered architecture, design patterns, externalized configuration, and centralized logging.

This project serves as a **portfolio-quality reference**, an educational template, and a foundation suitable for real-world automation teams.

---

## 🎯 Project Goals

- Build a **clean, extensible, and scalable** automation framework.  
- Provide **UI + API test automation** in one architecture.  
- Support **environment configuration** via Gradle properties.  
- Integrate **Log4j2** for structured logging.  
- Promote **separation of concerns** for maintainable architecture.  
- Prepare the framework for CI/CD, parallel execution, and reporting.

---
## 🚀 Tech Stack

| Component | Purpose |
|----------|---------|
| **Java 21** | Core language |
| **Log4j2**| Centralized logging system |
| **Selenium WebDriver 4** | UI automation |
| **Cucumber JVM 7** | BDD framework |
| **Gradle** | Build + dependency management |
| **WebDriverManager** | Automatic browser driver management |
| **JUnit** | Test runner |
| **Cucumber HTML/JSON reports** | Reporting |

---

# 🧩 Architecture Structure

```bash
VulcanTestFramework/
├── build.gradle
├── settings.gradle
├── gradle.properties
├── README.md
└── src/
    └── test/
        ├── java/
        │   └── com/vulcan/framework/
        │       ├── config/
        │       │   └── ConfigManager.java
        │       ├── core/
        │       │   └── DriverFactory.java
        │       ├── hooks/
        │       │   └── Hooks.java
        │       ├── ui/
        │       │   ├── pages/
        │       │   │   ├── BasePage.java
        │       │   │   └── LoginPage.java
        │       │   ├── actions/
        │       │   └── assertions/
        │       ├── api/
        │       │   ├── client/
        │       │   ├── models/
        │       │   ├── requests/
        │       │   └── assertions/
        │       ├── steps/
        │       │   ├── ui/
        │       │   │   └── LoginSteps.java
        │       │   └── api/
        │       └── runners/
        │           └── CucumberTestRunner.java
        └── resources/
            └── features/
                └── login.feature
```
# ⭐ Core Architectural Principles

The VulcanTestFramework is designed following strict engineering and software architecture standards to ensure clarity, maintainability, and scalability.

**Separation of Concerns**  
  Every layer has a single purpose:  
  - UI pages only contain locators + actions  
  - UI actions layer contains high-level flows  
  - Step definitions contain only business logic  
  - API clients handle requests  
  - API assertions validate responses  
  - Hooks manage browser lifecycle  
  - Core layer handles infrastructure

**⚙️ Configuration (gradle.properties)**  
All configuration is handled through `gradle.properties`: 
  ```properties
    # UI settings
    ui.baseUrl=https://www.saucedemo.com/
    ui.browser=chrome
    ui.implicitWait=5  
    # API settings
    api.baseUrl=https://api.example.com
    api.timeout=5000

    # Environment name
    env=dev
  ```
Values are injected into the JVM by Gradle and accessed using:
  ```java
  ConfigManager.getInstance().get("ui.baseUrl");
  ConfigManager.getInstance().get("api.baseUrl");
  ```
**🔧 Logging (Log4j2)**
The framework uses Log4j2 for structured, timestamped logging. 

Gradle configuration ensures logs appear in test output:
  ```groovy
  testLogging {
    events "PASSED", "FAILED", "SKIPPED"
    showStandardStreams = true
  }
  ```
All layers produce structured logs for debugging and observability. Logs include activity from:
- ConfigManager
- DriverFactory
- Hooks
- UI Pages
- API Clients

Example output:
```
[INFO ] ConfigManager - Reading ui.browser=chrome
[INFO ] DriverFactory - Creating WebDriver instance (chrome)
[INFO ] Hooks - Navigating to baseUrl: https://www.saucedemo.com/
[DEBUG] LoginPage - Checking if login form is visible
```

**Design Patterns**  
  - *Singleton*: ConfigManager  
  - *Factory*: DriverFactory  
  - *Page Object Pattern*: UI Pages  
  - *Facade / Actions Layer*: UI flows  
  - *API Client Pattern*: REST client abstractions  
  - *Separation of Assertions*: UI + API assertion modules

## 🧠 UI Architecture (Page Object Model)

**BasePage**
Shared UI behavior for all pages:
- WebDriver access
- PageFactory initialization
- Helper methods (click, type, isDisplayed)
- Logging included

**Page Objects (ui/pages/)**
Example: LoginPage
- Contains locators (@FindBy)
- Contains UI interaction logic
- Supports domain flows like loginAs(user, password)

**UI Steps (steps/ui/)**
- Cucumber BDD steps
- Step files act only as “scenario translators” and never contain Selenium or RestAssured logic.
- Call into LoginPage (never WebDriver directly)

## 🛰 API Architecture (Skeleton Ready)
The framework supports API testing using RestAssured, following the same clean structure as UI.

✔ api/client/BaseApiClient
- Reads api.baseUrl and api.timeout
- Configures RestAssured
- Defines helper methods (get, post, etc.)

✔ Domain clients (e.g., UserApiClient)
- Implements endpoint-specific operations
```code
getUserById(id)
createUser(payload)
```
✔ DTO models (api/models)
- Represent JSON response bodies
- Handled via Jackson

✔ API assertions (api/assertions)

Reusable checks for:
- Status codes
- Field equality
- JSON structure

✔ API stepdefs (steps/api)

Cucumber steps that interact with API clients.





- **Environment-Aware Execution**  
 Any value (UI base URL, API endpoint, browser type, wait times) can be overridden via:  
```bash
  ./gradlew test -Pui.browser=firefox -Penv=qa
```


# 🖖 VulcanTestFramework  
### © 2025 cpmn.tech — MIT License








## 🧪 Running Tests

### **Run the full test suite**
```bash
./gradlew test
```
### **Override browser**
```bash
./gradlew test -Pui.browser=firefox
```
### **Override UI Base URL**
```bash
./gradlew test -Pui.baseUrl=https://staging.example.com
```
### **Run tests with tags**
```bash
./gradlew test -Dcucumber.filter.tags="@smoke"
```
### **Override environment**
Future environments:
    - config-dev.properties
	- config-qa.properties
	- config-prod.properties
```bash
./gradlew test -Penv=qa
```

### **Configuration**
src/test/resources/config.properties
```bash
baseUrl=https://www.tobedefined.com
browser=chrome
implicitWait=10
```


## 🧠 Design Decisions (The Vulcan Logic)
- Use Singleton for configuration
- Use Factory for WebDriver creation
- Use Page Object Model for maintainability
- Use thin step definitions for readability
- Use hooks for browser lifecycle orchestration
- Use external Gradle-based configuration
- Use consistent logging across layers
- Use clean architecture for UI + API extensibilit
---

## 📈 CI/CD Integration (Coming Soon)
- GitHub Actions workflow will include:
- Java + Gradle setup
- UI + API test execution
- Publishing test reports
- Build badge in README
---

## 📝 Contributing
Even as a personal project, professional practices are followed:
Feature branches
- Meaningful commit messages
- Modular code
- Clear documentation
- Consistent naming conventions    

# 🖖 VulcanTestFramework  
### © 2025 cpmn.tech — MIT License
 