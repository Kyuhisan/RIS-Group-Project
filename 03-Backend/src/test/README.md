# Testing Report

## Description of Tests

In this task, we performed unit testing for our Spring Boot-based application, which includes features such as task management and task group management. We tested various components of the application, such as controllers, models, and repositories, to ensure that they work correctly and without errors.

### Types of Tests

1. **Controller Testing**:
   - **TaskControllerTest**: We tested basic CRUD operations for `TaskController`, including creating, deleting, updating, and retrieving tasks.
   - **TaskGroupControllerTest**: We tested CRUD operations for `TaskGroupController` to ensure the correct handling of tasks associated with task groups.

2. **Testing with Different Annotations**:
   - **TaskControllerParameterizedTest**: We tested various scenarios for the `getTaskById` method using parameterized tests to check how the application behaves under different conditions.
   - **TaskControllerRepeatedTest**: We tested repeatable scenarios to verify whether the application consistently functions when the same functionality is executed multiple times.
   - **TaskControllerDynamicTest**: Dynamic tests were used to test various methods for task creation, checking how the application behaves with different input data and ensuring correct results.

3. **Model Testing**:
   - **TaskModelTest**: We verified if tasks are correctly created within the `Task` model, including checking for data validation.
   - **TaskGroupModelTest**: We tested the correct operation of the `TaskGroup` model and validated whether tasks are correctly associated with task groups.

4. **Repository Testing**:
   - **TaskRepositoryTest**: We tested the `TaskRepository` to ensure correct task storage and retrieval from the database.
   - **TaskGroupRepositoryTest**: We tested `TaskGroupRepository` to ensure task groups are correctly saved and retrieved from the database.

### Why Are These Tests Important?

These tests ensure that our application functions correctly under different conditions and handles a wide range of input data. By identifying errors early in the testing phase, we reduce the likelihood of bugs in production. Testing CRUD operations and interactions between entities ensures the integrity of data and the correct execution of business logic.

---

## Team Members and Responsibilities

| Team Member       | Responsibility |
|-------------------|----------------|
| **Filip Rap**   | Testing `TaskController` |
| **Matic Kuhar**    | Testing `TaskGroupController` |
| **Rene Laufer**   | Testing `TaskModel`, `TaskGroupModel`, `TaskRepository`, `TaskGroupRepository`, `DataInitializer` |

---

## Brief Analysis of Test Success

### Test Results

The initial test execution resulted in nearly all negative test scenarios failing, especially with the additional "complex" functionality of task grouping. This was due to insufficient or non-existant error handling in our code, which we by the time of the latest project version already solved.
1. **Positive Scenarios**: We tested functionalities where we expected a successful output, such as retrieving a task by ID, creating new tasks, updating task statuses, and deleting tasks. Similarily tests for group functionality were also executed in similar matter.
2. **Negative Scenarios**: We tested edge cases, such as searching for non-existent tasks, and ensured the application correctly returns errors or empty responses as well as group tests for each group endpoint. This is where we had the most issues as we didn't have sufficient error handling ready.
3. **Mixed Scenarios**: Using parameterized and repeated tests, we tested the application under various conditions to ensure it behaves consistently and without errors.

### Errors Found

During testing, we did not find any errors or unexpected behaviors when it comes to positive testing scenarios. But when we implemented negative testing scenarios most of them failed and we had to update our code to correctly handle any possible error, for which we used GlobalExceptionHandler class which forwarded errors as responses and correctly threw errors based on the issues and status codes. The endpoints were then updated to work with the GlobalExceptionHandler as a intermediate.

---

## README File Update

The README documentation has been updated with the following changes:
- A folder for unit tests has been added under `src/test/java/si/um/feri/Backend/testiranje/`.
- The README clearly indicates that tests have been performed for controllers, models, and repositories.
- A link to the testing report has been added, which includes details about the tests, responsibilities, and success analysis.

---

**End of Report**
