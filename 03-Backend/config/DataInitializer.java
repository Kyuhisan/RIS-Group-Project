package si.um.feri.Backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import si.um.feri.Backend.model.Task;
import si.um.feri.Backend.model.TaskGroup;
import si.um.feri.Backend.model.TaskStatus;
import si.um.feri.Backend.repository.TaskGroupRepository;
import si.um.feri.Backend.repository.TaskRepository;

import java.util.Arrays;

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
        TaskGroup group1 = new TaskGroup("Group 1", 0.0, null);
        TaskGroup group2 = new TaskGroup("Group 2", 0.0, null);

        taskGroupRepository.saveAll(Arrays.asList(group1, group2));

        Task task1 = new Task("Task 1", "Description 1", group1, TaskStatus.TODO);
        Task task2 = new Task("Task 2", "Description 2", group1, TaskStatus.IN_PROGRESS);
        Task task3 = new Task("Task 3", "Description 3", group2, TaskStatus.DONE);

        taskRepository.saveAll(Arrays.asList(task1, task2, task3));
    }
}