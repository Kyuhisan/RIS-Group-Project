package si.um.feri.Backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import si.um.feri.Backend.model.Task;
import si.um.feri.Backend.model.TaskGroup;
import si.um.feri.Backend.model.TaskStatus;
import si.um.feri.Backend.repository.TaskGroupRepository;
import si.um.feri.Backend.repository.TaskRepository;

import java.util.Arrays;
import java.util.Random;

@Component
public class DataInitializer implements CommandLineRunner {

    private final TaskRepository taskRepository;
    private final TaskGroupRepository taskGroupRepository;

    public DataInitializer(TaskRepository taskRepository, TaskGroupRepository taskGroupRepository) {
        this.taskRepository = taskRepository;
        this.taskGroupRepository = taskGroupRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Random random = new Random();

        // Generate 10 task groups with random values between 0.0 and 100.0
        TaskGroup group1 = new TaskGroup("Group 1", random.nextDouble() * 100, null);
        TaskGroup group2 = new TaskGroup("Group 2", random.nextDouble() * 100, null);
        TaskGroup group3 = new TaskGroup("Group 3", random.nextDouble() * 100, null);
        TaskGroup group4 = new TaskGroup("Group 4", random.nextDouble() * 100, null);
        TaskGroup group5 = new TaskGroup("Group 5", random.nextDouble() * 100, null);
        TaskGroup group6 = new TaskGroup("Group 6", random.nextDouble() * 100, null);
        TaskGroup group7 = new TaskGroup("Group 7", random.nextDouble() * 100, null);
        TaskGroup group8 = new TaskGroup("Group 8", random.nextDouble() * 100, null);
        TaskGroup group9 = new TaskGroup("Group 9", random.nextDouble() * 100, null);
        TaskGroup group10 = new TaskGroup("Group 10", random.nextDouble() * 100, null);

        // Save the groups
        taskGroupRepository.saveAll(Arrays.asList(group1, group2, group3, group4, group5, group6, group7, group8, group9, group10));

        // Generate tasks with random statuses and link them to the groups
        Task task1 = new Task("Task 1", "Description for Task 1", group1, TaskStatus.FINISHED);
        Task task2 = new Task("Task 2", "Description for Task 2", group1, TaskStatus.IN_PROGRESS);
        Task task3 = new Task("Task 3", "Description for Task 3", group1, TaskStatus.NOT_STARTED);

        Task task4 = new Task("Task 4", "Description for Task 4", group2, TaskStatus.NOT_STARTED);
        Task task5 = new Task("Task 5", "Description for Task 5", group2, TaskStatus.IN_PROGRESS);
        Task task6 = new Task("Task 6", "Description for Task 6", group2, TaskStatus.FINISHED);

        Task task7 = new Task("Task 7", "Description for Task 7", group3, TaskStatus.NOT_STARTED);
        Task task8 = new Task("Task 8", "Description for Task 8", group3, TaskStatus.IN_PROGRESS);
        Task task9 = new Task("Task 9", "Description for Task 9", group3, TaskStatus.FINISHED);

        Task task10 = new Task("Task 10", "Description for Task 10", group4, TaskStatus.NOT_STARTED);
        Task task11 = new Task("Task 11", "Description for Task 11", group4, TaskStatus.IN_PROGRESS);
        Task task12 = new Task("Task 12", "Description for Task 12", group4, TaskStatus.FINISHED);

        Task task13 = new Task("Task 13", "Description for Task 13", group5, TaskStatus.NOT_STARTED);
        Task task14 = new Task("Task 14", "Description for Task 14", group5, TaskStatus.IN_PROGRESS);
        Task task15 = new Task("Task 15", "Description for Task 15", group5, TaskStatus.FINISHED);

        Task task16 = new Task("Task 16", "Description for Task 16", group6, TaskStatus.NOT_STARTED);
        Task task17 = new Task("Task 17", "Description for Task 17", group6, TaskStatus.IN_PROGRESS);
        Task task18 = new Task("Task 18", "Description for Task 18", group6, TaskStatus.FINISHED);

        Task task19 = new Task("Task 19", "Description for Task 19", group7, TaskStatus.NOT_STARTED);
        Task task20 = new Task("Task 20", "Description for Task 20", group7, TaskStatus.IN_PROGRESS);
        Task task21 = new Task("Task 21", "Description for Task 21", group7, TaskStatus.FINISHED);

        Task task22 = new Task("Task 22", "Description for Task 22", group8, TaskStatus.FINISHED);
        Task task23 = new Task("Task 23", "Description for Task 23", group8, TaskStatus.IN_PROGRESS);
        Task task24 = new Task("Task 24", "Description for Task 24", group8, TaskStatus.FINISHED);

        Task task25 = new Task("Task 25", "Description for Task 25", group9, TaskStatus.FINISHED);
        Task task26 = new Task("Task 26", "Description for Task 26", group9, TaskStatus.IN_PROGRESS);
        Task task27 = new Task("Task 27", "Description for Task 27", group9, TaskStatus.FINISHED);

        Task task28 = new Task("Task 28", "Description for Task 28", group10, TaskStatus.FINISHED);
        Task task29 = new Task("Task 29", "Description for Task 29", group10, TaskStatus.IN_PROGRESS);
        Task task30 = new Task("Task 30", "Description for Task 30", group10, TaskStatus.FINISHED);

        // Save the tasks
        taskRepository.saveAll(Arrays.asList(
                task1, task2, task3, task4, task5, task6, task7, task8, task9,
                task10, task11, task12, task13, task14, task15, task16, task17, task18,
                task19, task20, task21, task22, task23, task24, task25, task26, task27,
                task28, task29, task30
        ));
    }
}