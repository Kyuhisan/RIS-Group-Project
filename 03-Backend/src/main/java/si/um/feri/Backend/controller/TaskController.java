package si.um.feri.Backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import si.um.feri.Backend.model.Task;
import si.um.feri.Backend.repository.TaskRepository;

import java.util.logging.Logger;

@RestController
@CrossOrigin("http://localhost:5173")
public class TaskController {
    Logger logger = Logger.getLogger(TaskController.class.getName());

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/info")
    public String info() {
        return "Hello from the backend!";
    }

    @GetMapping("/tasks")
    public Iterable<Task> getAllTasks() {
        logger.info("Getting all tasks");
        return taskRepository.findAll();
    }

    @GetMapping("/task/{id}")
    public Task getTaskById(@PathVariable int id) {
        logger.info("Getting task with id:" + id);
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found with id:" + id));
    }

    @PutMapping("/task/{id}")
    public Task updateTask(@RequestBody Task newTask, @PathVariable int id) {
        logger.info("Updating task with id:" + id);
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTaskName(newTask.getTaskName());
                    task.setTaskDescription(newTask.getTaskDescription());
                    task.setTaskGroup(newTask.getTaskGroup());
                    task.setStatus(newTask.getStatus());
                    return taskRepository.save(task);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found with id:" + id));
    }

    @DeleteMapping("/task/{id}")
    public String deleteTask(@PathVariable int id) {
        logger.info("Deleting task with id:" + id);
        if (!taskRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found with id:" + id);
        }
        taskRepository.deleteById(id);
        return "Task with id: " + id + " has been deleted.";
    }

    @PostMapping("/tasks")
    public Task newTask(@RequestBody Task newTask) {
        logger.info("Creating new task");
        if (newTask.getTaskName() == null || newTask.getTaskName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task name is required");
        }
        return taskRepository.save(newTask);
    }
}