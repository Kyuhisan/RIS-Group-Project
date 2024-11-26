Authors: Matic Kuhar, Rene Laufer, Filip Rap
# RIS-Group-Project
A TO-DO application for task management supports creating and deleting categories/groups of tasks that form a whole. Each group can contain any number of smaller tasks, which can be assigned one of the statuses: Done, WIP (Work In Progress), or Unfinished. Based on the status of these subtasks, the application calculates and displays our progress in the form of a colored progress bar. Each subtask can also be deleted.

## Table of Contents
- [Documentation](#Documentation)
- [Installation](#Installation)
- [Contributing](#Contributing)
- [License](#License)
- [Vocabulary](#Vocabulary)	

## Documentation: 

### Our Vision
Our TO-DO application is designed for easy to track task management via task groups. These task groups allow users to organize related tasks together and track their progress more easily through easy to read visual interface. Besides simple task recording, implementing task groups goes beyond standard task tracking applications by offering structured handling and management of complex tasks. Further enhancing that is task progression tracking. By implementing status codes users can now visually see the progress of each task group by nicely color coded progress bar. All this together enables users to quickly assess how close to the completion they are with any particular task which can range from simple to more complex.

Application is intended for any user that prefers visual task management instead of monotonous alternatives. It is great for tracking simple tasks but shines when it comes to complex projects where users can easily track their progress of tasks that require multiple smaller tasks to be completed. Therefore the application will find it's way mostly into hands of students, freelancers and team managers who work on shared projects.

Applications function wise offer all the necessary functions like creating, editing and deleting both individual as well as group tasks via simple interface interactions. This highly improves productivity and ease of use, which keeps users motivated to keep their tasks recorded and organized as they won't see task management as a chore, but rather a tool that improves their productivity.

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
   git clone https://github.com/Kyuhisan/RIS-Group-Project.git
   cd RIS-Group-Project
2. Create MySQL DB:
  Create a user on localhost
  Set username and password in application.properties
  Run RIS_Project.sql
3. Establish frontend:
  Run "npm install" to get the modules
4. Establish backend:
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

## DPU

![DPU](https://github.com/user-attachments/assets/83db31ac-5b7e-4a3a-a1b7-18f7fb367293)

## Functionality Discription

### Task Manipulation
| Use Case             | ID: 20 |
|------------------------------|---------|
| **Goal**                     | Change a single task. |
| **Actors**                  | End user, System. |
| **Prerequisites**               | There is at least one task. |
| **System State After PU**     | The system displays the changes. |
| **Scenario**                 | 1. The user opens the application. <br> 2. The user presses one of the buttons displayed on the task. <br> 3. The user deletes/changes name of the task. <br> 4. The system make corresponding changes in the database. <br> 5. The system displays the changes to the user. |
| **Alternative Flows**      | 2.1.1 User pressed "Edit" button. <br>&emsp; 2.1.2 User enters new name for the task. <br>&emsp; 2.1.3 User confirms the change of name. <br>&emsp; 2.1.4 System changes the name of the task in database. <br> 2.2.1 User pressed "Delete" button. <br>&emsp; 2.2.2 User confirms deletion of the task. <br>&emsp; 2.2.3 System deletes the task from database. |
| **Exceptions**                   | 	Error saving task information (displays an error message). |

### Task Overview
| Use Case             | ID: 30 |
|------------------------------|---------|
| **Goal**                     | Display all available tasks. |
| **Actors**                  | End user, System. |
| **Prerequisites**               | There is at least one task and one group. |
| **System State After PU**     | The system displays all current tasks. |
| **Scenario**                 | 1. The user opens the application. <br> 2. The system displays a list of all groups and all tasks in those groups inside the body of the application. |
| **Alternative Flows**      | 1. There are no tasks so the system displays a message (There are no tasks to show!). <br> 2. There are no groups, threfore there can't be any tasks. (There are no tasks to show!). |
| **Exceptions**                   | 	2.1 Error loading tasks (displays an error message). |

### Task Search
| Primer uporabe              | ID: 31 |
|------------------------------|---------|
| **Goal**                     | Tasks can be searched. |
| **Actors**                  | End user, System. |
| **Prerequisites**               | There is at least one task inside the database. |
| **System State After PU**     | The system displays search results based on the entered query. |
| **Scenario**                 | 1. The user opens the application. <br> 2. A search field appears at the top of the application. <br> 3. The user enters a search query. <br> 4. System in real time searches the database for matching results. <br> 5. The system displays search results. |
| **Alternative Flows**      | If no tasks match the search criteria, the system displays a message (No results found!). |
| **Exceptions**                   | 5.1	Search error (displays an error message). |

### Task Group Creation
| Primer uporabe              | ID: 40 |
|------------------------------|---------|
| **Goal**                     | A new empty task group is created. |
| **Actors**                  | End user, System. |
| **Prerequisites**               | The user has entered the group name. |
| **System State After PU**     | A new group is added to the database. |
| **Scenario**                 | 1. The user opens the application. <br> 2. The user enters the group name. <br> 3. The user adds the group to the system. <br> 4. The application saves the group to the database. |
| **Alternative Flows**      | The user does not enter a group name (displays an error message). |
| **Exceptions**                   |  <br> 3.1. Error creating the group (displays an error message).	4.1. Error saving the group (displays an error message). |
   
### Display Task Group Progress
| Primer uporabe              | ID: 41 |
|------------------------------|---------|
| **Goal**                     | Visual representation of progress in the form of a color-coded progress bar. |
| **Actors**                  | End user, System. |
| **Prerequisites**               | At least one group exists in the system. |
| **System State After PU**     | A color-coded progress bar appears in the user interface. |
| **Scenario**                 | 	1. The user opens the application. <br> 2. The system retrieves the list of groups from the database. <br> 3. The system displays the groups in the user interface. <br> 4. The system reads the statuses of tasks within each group. <br> 5. The system calculates progress based on task statuses. <br> 6. The system updates each group’s progress bar. <br> 7. The system displays the updated progress. |
| **Alternative Flows**      | The system does not find the group. |
| **Exceptions**                   | 	1. Error finding the group in the database (displays an error message). <br> 2. Error displaying progress (displays an error message). <br> 3. Error calculating progress (displays an error message). |

### Task Group Deletion
| Primer uporabe              | ID: 42 |
|------------------------------|---------|
| **Goal**                     | Group is deleted from the system. |
| **Actors**                  | End user, System. |
| **Prerequisites**               | A group exists that the user wants to delete. |
| **System State After PU**     | The group is deleted from the database and removed from the system. |
| **Scenario**                 | 1. The user opens the application. <br> 2. The user selects a group. <br> 3. The user clicks on "delete". <br> 4. The application sends the request to the database. <br> 5. The application deletes the group from the database. <br> 6. The system is updated without the deleted group. |
| **Alternative Flows**      | Wrong group is deleted. |
| **Exceptions**                   | 	1. User error during group deletion (displays an error message). <br> 2. Error deleting the group from the database (displays an error message). |

### Task Addition
| Primer uporabe              | ID: 43 |
|------------------------------|---------|
| **Goal**                     | Task is added to a group. |
| **Actors**                  | End user, System. |
| **Prerequisites**               | 	1. The group exists. <br> 2. All data is entered. |
| **System State After PU**     | A new task is added to the group. |
| **Scenario**                 | 	1. The user opens the application. <br> 2. The user selects the option to add a new task within a group. <br> 3. The system displays a form. <br> 4. The user enters the task details. <br> 5. The user saves the task. <br> 6. The system saves the task. <br> 7. The system displays the updated list. |
| **Alternative Flows**      | 	1. The user does not enter all required information (displays an error message). <br> 2. The user selects the wrong group |
| **Exceptions**                   | Error saving the task (displays an error message). |

### Task Status Change
| Primer uporabe              | ID: 44 |
|------------------------------|---------|
| **Goal**                     | Task status is changed. |
| **Actors**                  | End user, System. |
| **Prerequisites**               | Task exists. |
| **System State After PU**     | Task status is changed and progress is updated. |
| **Scenario**                 | 1. The user opens the application. <br> 2. The user selects the option to change the task status within a group. <br> 3. The user selects the appropriate status. <br> 4. The system sends the request to the database. <br> 5. The system updates the task status in the database. <br> 6. The system recalculates progress. <br> 7. The system displays the updated status and progress in a color-coded progress bar. |
| **Alternative Flows**      | User selects the existing status. |
| **Exceptions**                   | 1. Error changing task status (displays an error message). <br> 2. Error recalculating progress (displays an error message). <br> 3. Error saving the updated status to the database (displays an error message). |

## Classes and Their Roles

1. **Task**
   - **Role**: Represents an individual task.
   - **Attributes**:
     - `taskID`: Unique identifier for the task.
     - `taskName`: Name of the task.
     - `taskStatus`: Status of the task (e.g., IN_PROGRESS, FINISHED, NOT_STARTED).
     - `taskDescription`: Description of the task.
     - `taskGroup`: The group to which the task belongs.

2. **TaskDTO**
   - **Role**: Data transfer object for tasks without unnecessary details.
   - **Attributes**:
     - `taskID`: Unique identifier for the task.
     - `taskName`: Name of the task.
     - `taskStatus`: Status of the task.

3. **Task Services**
   - **Role**: Business logic for managing tasks.
   - **Methods**:
     - `createTask(taskDTO: TaskDTO): Task`: Creates a new task.
     - `editTask(taskID: int, taskDTO: TaskDTO): Task`: Edits an existing task.
     - `deleteTask(taskID: int): void`: Deletes a task.
     - `getTask(taskID: int): Task`: Retrieves a task by ID.
     - `getAllTasks(): List`: Retrieves all tasks.

4. **TaskController**
   - **Role**: Manages HTTP requests for tasks.
   - **Methods**:
     - `addTask(taskDTO: TaskDTO): Task`: Adds a new task.
     - `updateTask(taskID: int, taskDTO: TaskDTO): Task`: Updates an existing task.
     - `removeTask(taskID: int): void`: Removes a task.
     - `retrieveTask(taskID: int): Task`: Retrieves a task by ID.
     - `retrieveAllTasks(): List`: Retrieves all tasks.

5. **TaskRepository**
   - **Role**: Manages the database for tasks.
   - **Methods**:
     - `findTaskByID(taskID: int): Task`: Finds a task by ID.
     - `saveTask(task: Task): Task`: Saves a task.
     - `deleteTask(taskID: int): void`: Deletes a task.
     - `findAllTasks(): List`: Finds all tasks.

6. **Group**
   - **Role**: Represents a group of tasks.
   - **Attributes**:
     - `groupID`: Unique identifier for the group.
     - `groupName`: Name of the group.
     - `groupProgress`: Progress of the group.
     - `listOfTasks`: List of tasks in the group.

7. **GroupDTO**
   - **Role**: Data transfer object for groups without unnecessary details.
   - **Attributes**:
     - `groupID`: Unique identifier for the group.
     - `groupName`: Name of the group.
     - `groupProgress`: Progress of the group.

8. **Group Services**
   - **Role**: Business logic for managing groups.
   - **Methods**:
     - `createGroup(groupDTO: GroupDTO): Group`: Creates a new group.
     - `editGroup(groupID: int, groupDTO: GroupDTO): Group`: Edits an existing group.
     - `deleteGroup(groupID: int): void`: Deletes a group.
     - `getGroup(groupID: int): Group`: Retrieves a group by ID.
     - `getAllGroups(): List`: Retrieves all groups.

9. **GroupController**
   - **Role**: Manages HTTP requests for groups.
   - **Methods**:
     - `addGroup(groupDTO: GroupDTO): Group`: Adds a new group.
     - `updateGroup(groupID: int, groupDTO: GroupDTO): Group`: Updates an existing group.
     - `removeGroup(groupID: int): void`: Removes a group.
     - `retrieveGroup(groupID: int): Group`: Retrieves a group by ID.
     - `retrieveAllGroups(): List`: Retrieves all groups.

10. **GroupRepository**
    - **Role**: Manages the database for groups.
    - **Methods**:
      - `findGroupByID(groupID: int): Group`: Finds a group by ID.
      - `saveGroup(group: Group): Group`: Saves a group.
      - `deleteGroup(groupID: int): void`: Deletes a group.
      - `findAllGroups(): List`: Finds all groups.

11. **SearchService**
    - **Role**: Provides search functionality for tasks and groups.
    - **Attributes**:
      - `searchQuery`: The query string for searching.
    - **Methods**:
      - `searchTasks(searchQuery: String): List`: Searches for tasks based on the query.
      - `searchGroups(searchQuery: String): List`: Searches for groups based on the query.

12. **TaskStatus**
    - **Role**: Enum for task statuses.
    - **Values**:
      - `IN_PROGRESS`: 0
      - `FINISHED`: 1
      - `NOT_STARTED`: 2

### Key Methods and Their Tasks

- **createTask** and **createGroup**: Creating new entities (tasks and groups).
- **editTask** and **editGroup**: Editing existing entities.
- **deleteTask** and **deleteGroup**: Deleting entities.
- **getTask** and **getGroup**: Retrieving individual entities by ID.
- **getAllTasks** and **getAllGroups**: Retrieving all entities.
- **searchTasks** and **searchGroups**: Searching for tasks and groups based on a query.

## Class Diagram
![To-Do Class Diagram - Final 3 vpd (1) vpd (1)](https://github.com/user-attachments/assets/05f1dc15-b90d-4782-9729-53e8efb43a84)

## New Functionality: Progress Bar

### Description
We have implemented a new progress bar functionality in the Advanced TO-DO List application. This feature visually represents the progress of tasks within each task group, providing a clear and immediate understanding of how much work has been completed.

### How It Works
The progress bar calculates the percentage of completed tasks within each task group. It dynamically updates based on the status of the tasks:
- **Green**: 100% completed tasks.
- **Yellow**: More than 50% but less than 100% completed tasks.
- **Red**: 50% or fewer completed tasks.

The progress bar is displayed within each task group's row in the main table, showing the exact percentage of tasks completed.

### How to Test the New Functionality
1. **Run the Application**: Ensure your development server is running.
2. **Navigate to the Home Page**: Open the application in your browser and go to the home page.
3. **View Task Groups**: You will see a table listing all task groups. Each group will have a progress bar indicating the completion percentage of its tasks.
4. **Update Task Status**: Change the status of tasks within a group to see the progress bar update in real-time.
5. **Create and Delete Tasks**: Add new tasks or delete existing ones to observe changes in the progress bar.
