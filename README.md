# AI-Powered Personal Finance Coach

[![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)](LICENSE)
[![Contributions Welcome](https://img.shields.io/badge/Contributions-Welcome-brightgreen?style=for-the-badge)](CONTRIBUTING.md)
[![PRs Welcome](https://img.shields.io/badge/PRs-Welcome-blue?style=for-the-badge)](http://makeapullrequest.com)

A lightweight, terminal-based Java application that helps users manage their personal finances using the **50/30/20 budgeting rule**.  
It offers smart transaction tracking, real-time financial analysis, and AI-style coaching — all in a clean, easy-to-use terminal interface.

---

## Features

- Categorized tracking of **Income**, **Needs**, **Wants**, and **Savings**
- Real-time financial coaching based on the 50/30/20 rule
- Automatic data persistence using CSV
- Interactive and beginner-friendly terminal menu
- Clean Maven project structure ready for collaboration
- Open for contributions and new features

---

## Tech Stack

| Technology       | Purpose                          |
|------------------|----------------------------------|
| Java 17+         | Core language                    |
| Apache Maven     | Build & dependency management    |
| CSV              | Lightweight data persistence     |
| Terminal UI      | Simple interactive interface     |

---

## Installation & Setup

### Prerequisites

- OpenJDK 17 or higher  
- Apache Maven 3.6+

```bash
# Ubuntu / Debian
sudo apt update
sudo apt install openjdk-17-jdk maven
```

### Clone & Run

```bash
git clone https://github.com/bundlab/finance-coach.git
cd finance-coach

# Build the project
mvn clean compile

# Run the application
mvn exec:java
```

> Replace `bundlab` with the actual repository owner.

---

## How to Use

1. Launch the app with `mvn exec:java`
2. Use the interactive menu to:
   - Add income and expenses
   - View budget breakdown
   - Receive AI-powered financial insights
3. All data is automatically saved to `finance_data.csv`

---

## Project Structure

```plaintext
finance-coach-java/
├── src/main/java/com/financecoach/
│   ├── Main.java                 # Application entry point
│   ├── engine/                   # Business logic & analysis
│   └── model/                    # Data models
├── src/main/resources/
│   └── finance_data.csv          # Persistent storage
├── pom.xml
├── README.md
└── CONTRIBUTING.md
```

---

## Contributing

We welcome contributions from the community!  

Whether you want to fix a bug, improve the coaching logic, add new features, or improve documentation — your help is appreciated.

Please read our [Contributing Guide](CONTRIBUTING.md) before submitting a pull request.

---

## Contributors

Thanks to everyone who has contributed to this project!

<a href="https://github.com/bundlab/finance-coach/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=bundlab/finance-coach" alt="Contributors" />
</a>

---

## License

This project is open source and available under the [MIT License](LICENSE).

---

**Built with care to help people develop better financial habits.**