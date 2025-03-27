# Socio-Health Information Management System

## Overview

This application is designed to manage socio-health monitoring data for the prevention of outbreaks and pandemics. It enables authorized personnel to collect, update, visualize, and analyze epidemiological data across different geographic levels.

Developed as part of a Software Engineering course project for the Bachelor’s Degree in Computer Science.

**Academic Year:** 2019-2020  
**Authors:**  
- Simone Brunello   
- [Daniele Nicoletti](https://github.com/Salazar99)   
- [Nicholas Nicolis](https://github.com/NicholasNicolis)

---

## Features

The system supports different user profiles, each with specific roles and privileges:

- **Contract Personnel (PCN):**  
  - Enter weekly epidemiological data for assigned municipalities.
  - Update data regarding patients under general care or in intensive care.

- **Entity Personnel (PE0, PE1, PE2):**  
  - **PE0:** Manage geographical data (regions and municipalities).
  - **PE1:** Manage local municipal representatives.
  - **PE2:** Enter and edit epidemiological data at the provincial level.

- **Analysts (ANL):**  
  - Visualize and export data.
  - Generate charts and perform temporal data analysis.

---

## Architecture

The system is designed following the **Model-View-Controller (MVC)** architectural pattern.

### Model
- Represented by the `Modello.java` class.
- Manages data and mediates communication between components.
- Follows the **Singleton Pattern** to ensure a single source of truth for data.

### View
- Built with JavaFX and SceneBuilder.
- Each FXML interface is linked to a controller.

### Controller
- Reacts to user interactions and updates the model and views accordingly.

Additional design pattern:
- **Factory Pattern**: Used to instantiate users based on their role.

---

## Data Management

Instead of using a traditional database, the system uses Excel spreadsheets to simulate data storage. The following files are used:

- `zone.xlsx` – Contains geographic hierarchies.
- `utenti.xlsx` – Stores user credentials and permissions.
- `datiProvincia.xlsx` – Annual death statistics per province.
- `datiComune.xlsx` – Weekly epidemiological data per municipality.

Excel files are managed using the Apache POI library.

---

## Development Process

- Followed an **Agile and Incremental** methodology.
- UI developed first to identify required functionality.
- Code versioning handled with JetBrains Space.
- Implementation tested continuously with manual and collaborative testing.

---

## Testing

- The application was tested iteratively during development.
- Additional user testing was conducted with peers and non-technical users.
- Automated testing (e.g., JUnit) was not used.

---

## Class Diagrams and Sequence Diagrams

The documentation includes:
- Use case diagrams for each user role.
- Activity diagrams for system interactions.
- Class and sequence diagrams showcasing data flow and architecture.

---

## Requirements

- Java (JDK)
- JavaFX
- SceneBuilder
- Apache POI (for Excel file handling)
- IntelliJ IDEA (recommended IDE)

---

