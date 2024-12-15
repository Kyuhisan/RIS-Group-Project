package si.um.feri.Backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import si.um.feri.Backend.model.Task;
import si.um.feri.Backend.model.TaskGroup;
import si.um.feri.Backend.model.TaskStatus;
import si.um.feri.Backend.repository.TaskRepository;

@Service
public class TaskGroupService {
    @Autowired
    private TaskRepository taskRepository;

    public TaskGroup duplicateTaskGroup(TaskGroup ogTaskGroup) {
        TaskGroup newTaskGroup = new TaskGroup();
        newTaskGroup.setGroupName(ogTaskGroup.getGroupName());
        newTaskGroup.setGroupProgress(0);
        newTaskGroup.setPeriod(ogTaskGroup.getPeriod());
        newTaskGroup.setFileBlob(ogTaskGroup.getFileBlob());
        newTaskGroup.setFileName(ogTaskGroup.getFileName());

        ogTaskGroup.getListOfTasks().forEach(task -> {
            Task newTask = new Task();
            newTask.setTaskName(task.getTaskName());
            newTask.setTaskDescription(task.getTaskDescription());
            newTask.setTaskGroup(newTaskGroup);
            newTask.setStatus(TaskStatus.NOT_STARTED);
            taskRepository.save(newTask);
        });

        return newTaskGroup;
    }
}
