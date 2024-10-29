# RIS-Group-Project
A TO-DO application for task management supports creating and deleting categories/groups of tasks that form a whole. Each group can contain any number of smaller tasks, which can be assigned one of the statuses: Done, WIP (Work In Progress), or Unfinished. Based on the status of these subtasks, the application calculates and displays our progress in the form of a colored progress bar. Each subtask can also be deleted.

## Table of Contents
- [Documentation](#Documentation)
- [Installation](#Installation)
- [Contributing](#Contributing)
- [License](#License)	

## Documentation: 

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

## Contributing.
All changes must be reviewed via Pull Requests.
Tests must pass successfully before merging.
Follow coding and documentation standards.

## Vocabulary

| Word | Discription |
| ------------- | ------------- |
Task |	Task is the main data processed by our configuration. In fact, it represents a task or simply a reminder that the user must complete and write it down in our application so he doesn't forget. In addition, the application offers him the option of appropriately marking, grouping and describing this task.
Task name	| With task name, the user names an individual task. The naming can be arbitrary and is basically of the string type. For each new task, the program automatically assigns an ID, which is a unique key for each task.
Task disription	| Task description represents a space where the user can describe an individual task. The description can be as long as you like and is helpful for the user to find their way around when reviewing past tasks.
Task group	| The user has the option of grouping individual tasks into groups. It does this by assigning them a task group. Tasks with the same task group belong to the same group. This makes it easier for the user to carry out more demanding tasks, as they can be divided into several parts.
Task status	| The task performed by the user goes through three different statuses: Unfinished, WIP (Working in process) and Done. Depending on the state of the current task, the user can mark it on the application.
Task action	| Task action allows the user to edit or delete an individual task.


