package si.um.feri.Backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import si.um.feri.Backend.model.Task;
import si.um.feri.Backend.repository.TaskRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTasks() {
        when(taskRepository.findAll()).thenReturn(List.of(new Task(), new Task()));
        Iterable<Task> tasks = taskController.getAllTasks();
        assertNotNull(tasks);
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetTaskById() {
        Task task = new Task();
        task.setId(1);
        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        Task foundTask = taskController.getTaskById(1);
        assertEquals(1, foundTask.getId());
        verify(taskRepository, times(1)).findById(1);
    }

    @Test
    void testGetTaskById_NotFound() {
        when(taskRepository.findById(1)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            taskController.getTaskById(1);
        });
        assertEquals("Task not found with id:1", exception.getMessage());
    }

    @Test
    void testUpdateTask() {
        Task existingTask = new Task();
        existingTask.setId(1);
        Task newTask = new Task();
        newTask.setTaskName("Updated Task");
        when(taskRepository.findById(1)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(existingTask)).thenReturn(existingTask);

        Task updatedTask = taskController.updateTask(newTask, 1);
        assertEquals("Updated Task", updatedTask.getTaskName());
        verify(taskRepository, times(1)).findById(1);
        verify(taskRepository, times(1)).save(existingTask);
    }

    @Test
    void testDeleteTask() {
        doNothing().when(taskRepository).deleteById(1);
        String response = taskController.deleteTask(1);
        assertEquals("Task with id: 1 has been deleted.", response);
        verify(taskRepository, times(1)).deleteById(1);
    }

    @Test
    void testNewTask() {
        Task newTask = new Task();
        when(taskRepository.save(newTask)).thenReturn(newTask);
        Task createdTask = taskController.newTask(newTask);
        assertNotNull(createdTask);
        verify(taskRepository, times(1)).save(newTask);
    }
}