package si.um.feri.Backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import si.um.feri.Backend.model.Task;
import si.um.feri.Backend.model.TaskGroup;
import si.um.feri.Backend.repository.TaskGroupRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@CrossOrigin("http://localhost:5173")
public class TaskGroupController {
    Logger logger = Logger.getLogger(TaskGroupController.class.getName());

    @Autowired
    TaskGroupRepository taskGroupRepository;

    @GetMapping("/groups")
    public Iterable<TaskGroup> getAllGroups() {
        logger.info("Getting all groups");
        return taskGroupRepository.findAll();
    }

    @GetMapping("/group/{id}")
    public Map<String, Object> getGroupById(@PathVariable int id) {
        logger.info("Getting group with id: " + id);
        TaskGroup taskGroup = taskGroupRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TaskGroup not found with id:" + id));

        Map<String, Object> response = new HashMap<>();
        response.put("id", taskGroup.getId());
        response.put("groupName", taskGroup.getGroupName());
        response.put("groupProgress", taskGroup.getGroupProgress());
        response.put("listOfTasks", taskGroup.getListOfTasks());
        response.put("fileBlob", taskGroup.getFileBlob());
        response.put("fileName", taskGroup.getFileName());
        response.put("containsFile", taskGroup.getFileBlob() != null && taskGroup.getFileBlob().length > 0);

        return response;
    }

    @GetMapping("/group/{id}/has-file")
    public Map<String, Boolean> doesGroupContainFile(@PathVariable int id) {
        logger.info("Checking if TaskGroup with ID: " + id + " contains a file");
        TaskGroup taskGroup = taskGroupRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TaskGroup not found with ID: " + id));

        boolean containsFile = taskGroup.getFileBlob() != null && taskGroup.getFileBlob().length > 0;

        Map<String, Boolean> response = new HashMap<>();
        response.put("containsFile", containsFile);
        return response;
    }

    @PutMapping("/group/{id}")
    public TaskGroup updateGroup(@RequestBody TaskGroup newTaskGroup, @PathVariable int id) {
        logger.info("Updating group with id: " + id);
        return taskGroupRepository.findById(id).map(taskGroup -> {
            taskGroup.setGroupName(newTaskGroup.getGroupName());
            taskGroup.setGroupProgress(newTaskGroup.getGroupProgress());
            taskGroup.setListOfTasks(newTaskGroup.getListOfTasks());
            return taskGroupRepository.save(taskGroup);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TaskGroup not found with id:" + id));
    }

    @PatchMapping("/group/{id}/file")
    public TaskGroup updateGroupFile(@PathVariable int id, @RequestBody TaskGroup newTaskGroup) {
        logger.info("Updating file for group with id: " + id);
        return taskGroupRepository.findById(id).map(taskGroup -> {
            if(newTaskGroup.getFileBlob() != null) {
                taskGroup.setFileBlob(newTaskGroup.getFileBlob());
            }
            if (newTaskGroup.getFileName() != null) {
                taskGroup.setFileName(newTaskGroup.getFileName());
            }
            return taskGroupRepository.save(taskGroup);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TaskGroup not found with id:" + id));
    }

    @DeleteMapping("group/{id}")
    public String deleteGroup(@PathVariable int id) {
        logger.info("Deleting group with id: " + id);
        if (taskGroupRepository.existsById(id)) {
            taskGroupRepository.deleteById(id);
            return "Group with id: " + id + " has been deleted.";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TaskGroup not found with id:" + id);
        }
    }

    @PostMapping("/group")
    public TaskGroup addGroup(@RequestBody TaskGroup newTaskGroup) {
        logger.info("Adding group with name: " + newTaskGroup.getGroupName());
        if (newTaskGroup.getGroupName() == null || newTaskGroup.getGroupName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Group name is required.");
        }
        return taskGroupRepository.save(newTaskGroup);
    }

    @GetMapping("/group-tsk/{id}")
    public List<Task> getAllGroupTasks(@PathVariable int id) {
        logger.info("Getting all tasks for group with id: " + id);
        return taskGroupRepository.findById(id)
                .map(TaskGroup::getListOfTasks)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TaskGroup not found with id:" + id));
    }
}
