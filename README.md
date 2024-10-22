# RIS-Group-Project
A TO-DO application for task management supports creating and deleting categories/groups of tasks that form a whole. Each group can contain any number of smaller tasks, which can be assigned one of the statuses: Done, WIP (Work In Progress), or Unfinished. Based on the status of these subtasks, the application calculates and displays our progress in the form of a colored progress bar. Each subtask can also be deleted.

## Table of Contents
- [Documentation](#Documentation)
- [Installation](#Installation)
- [Contributing](#Contributing)
- [License](#License)	

## Documentation: 
- Opis projektne strukture, standardi kodiranja, informacije o uporabljenih orodjih, frameworkih in različicah
- 
### Project Structure
- `src/`: Contains the project's source code.
  - `main/`: Main application.
  - `test/`: Test files.
- `docs/`: Project documentation.
- `config/`: Configuration files.
- `scripts/`: Utility scripts.

### Coding Standards
- We use Prettier for code formatting.
- We follow the Airbnb JavaScript Style Guide.

### Tools and Frameworks Used
- **Springboot**: Version 3.3.4
- **MySQL**: Version 8.0.0
- **React**: Version 17.x
- **IntelliJ IDEA Ultimate**: Version 2024.2.3
- **VS Studio Code**: Version 1.94

  
### Prerequisites
- Node.js (14.x)
- npm (6.x) or yarn (1.x)
- MySQL (8.x)
- JDK (11 or newest)
- Maven (3.6.x or newest)

  
## Installation Instructions

### Installation Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/Kyuhisan/RIS-Group-Project.git
   cd RIS-Group-Project
2. Create MySQL DB:
<<<<<<< HEAD
  ```bash
  Create a user on localhost
  Set username and password in application.properties
  Run RIS_Project.sql
3. Establish frontend:
  ```bash
  Run "npm install" to get the modules
4. Establish backend:
  ```bash
  Run "BackendApplication" through InteliJ 
=======
    ```bash
    Create a user on localhost
    Set username and password in application.properties
    Run RIS_Project.sql
3. Establish frontend:
    ```bash
    Run "npm install" to get the modules
4. Establish backend:
    ```bash
    Run "BackendApplication" through InteliJ
>>>>>>> 4d405d6e1941970cc2a9075e0b306bc9feabecee

## Contributing.
All changes must be reviewed via Pull Requests.
Tests must pass successfully before merging.
Follow coding and documentation standards.

