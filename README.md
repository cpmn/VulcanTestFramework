<!--

VulcanTestFramework
A QA Automation Project by Claudia Paola Muñoz (cpmn.tech)

Copyright (c) 2025
MIT License
  
-->

# 🖖 VulcanTestFramework  
**A logical, scalable, and modular UI Automation Framework built with Java, Selenium WebDriver, Cucumber (BDD), and Gradle.**

> *“Logic is the cement of our civilization.” — Spock*  
Inspired by Vulcan principles of clarity, precision, and efficiency.

---

## 🌌 Overview

**VulcanTestFramework** is a fully modular and extensible UI automation framework designed to demonstrate industry-level engineering practices.  
It provides a clean architecture following Page Object Model (POM), Behavior-Driven Development (BDD), and configurable execution through Gradle.

This project serves as a **professional starter-kit**, ideal for QA automation portfolios, learning, and real-world applications.

---

## 🎯 Project Goals

- Build a **clean, reusable, and scalable** automation framework.  
- Demonstrate **best practices** in Selenium + Java + Cucumber architecture.  
- Enable configuration per environment and per browser.  
- Provide rich reporting and CI/CD integration.  
- Document every component with clarity and logic (the Vulcan way).

---


## 🧩 Architecture Structure
```bash
VulcanTestFramework/
├─ build.gradle
├─ settings.gradle
├─ README.md
├─ /src
│  ├─ /test
│  │  ├─ /java
│  │  │  └─ com.vulcan.framework
│  │  │     ├─ hooks
│  │  │     ├─ steps
│  │  │     ├─ runners
│  │  │     ├─ support
│  │  │     │  ├─ DriverFactory.java
│  │  │     │  ├─ ConfigManager.java
│  │  │     │  ├─ BasePage.java
│  │  │     │  └─ utils
│  │  └─ /resources
│  │     ├─ features
│  │     └─ config.properties
```
### **Core Design Principles**
- **Logical simplicity**  
- **Single Responsibility Architecture**  
- **Reusability and extensibility**  
- **External configuration**  
- **Readable test definitions (BDD)**  

---

## 🚀 Tech Stack

| Component | Purpose |
|----------|---------|
| **Java 17** | Core language |
| **Selenium WebDriver 4** | UI automation |
| **Cucumber JVM 7** | BDD framework |
| **Gradle** | Build + dependency management |
| **WebDriverManager** | Automatic browser driver management |
| **JUnit** | Test runner |
| **Cucumber HTML/JSON reports** | Reporting |

---

## 🧪 Running Tests

### **Run all tests**
```bash
./gradlew test
```

### **Run tests with tags**
```bash
./gradlew test -Dcucumber.filter.tags="@smoke"
```

### **Override browser**
```bash
./gradlew test -Dbrowser=firefox
```

### **Configuration**
src/test/resources/config.properties
```bash
baseUrl=https://www.tobedefined.com
browser=chrome
implicitWait=10
```
Future environments:
    - config-dev.properties
	- config-qa.properties
	- config-prod.properties

Override from CLI:
```bash
./gradlew test -Denv=qa
```
## 🧠 Design Decisions (The Vulcan Logic)
    - Use Page Object Model for maintainability
	- Use ThreadLocal WebDriver to support parallel execution
	- Separate test logic from UI interaction
	- Use external configuration for flexibility
	- Use hooks to orchestrate browser lifecycle
	- Keep code minimal, clear, and predictable
---

## 📈 CI/CD Integration (Coming Soon)

    GitHub Actions workflow will include:
	- Java + Gradle setup
	- Test execution
	- Report publishing
	- Build badge in README
---

## 📝 Contributing
    
    Even as a personal project, standards are followed:
    - Feature branches
	- Meaningful commit messages
	- PR-style development
	- Code kept modular and documented

# 🖖 VulcanTestFramework  
### © 2025 cpmn.tech — MIT License
 