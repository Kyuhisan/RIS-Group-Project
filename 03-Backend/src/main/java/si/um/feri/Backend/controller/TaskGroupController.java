package si.um.feri.Backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import si.um.feri.Backend.model.Task;
import si.um.feri.Backend.model.TaskGroup;
import si.um.feri.Backend.repository.TaskGroupRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
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
    TaskGroup getGroupById(@PathVariable int id) {
        logger.info("Getting group with id:" + id);
        return taskGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TaskGroup not found with id:" + id));
    }

    @PutMapping("/group-upd/{id}")
    TaskGroup updateGroup(@RequestBody TaskGroup newTaskGroup, @PathVariable int id) {
        logger.info("Updating group with id:" + id);
        return taskGroupRepository.findById(id).map(
                taskGroup -> {
                  taskGroup.setGroupName(newTaskGroup.getGroupName());
                    taskGroup.setGroupProgress(newTaskGroup.getGroupProgress());
                    taskGroup.setListOfTasks(newTaskGroup.getListOfTasks());
                    return taskGroupRepository.save(taskGroup);
                }).orElseThrow(() -> new RuntimeException("TaskGroup not found with id:" + id));
    }

    @DeleteMapping("group/{id}")
    String deleteGroup(@PathVariable int id) {
        logger.info("Deleting group with id:" + id);
        taskGroupRepository.deleteById(id);
        return "Group with id: " + id + " has been deleted.";
    }

    @PostMapping("/group")
    TaskGroup addGroup(@RequestBody TaskGroup newTaskGroup) {
        logger.info("Adding group with name:" + newTaskGroup.getGroupName());
        return taskGroupRepository.save(newTaskGroup);
    }

   @GetMapping("/group-tsk/{id}")
   public List<Task> getAllGroupTasks(@PathVariable int id) {
       logger.info("Getting all group tasks");
       return taskGroupRepository.findById(id)
               .map(TaskGroup::getListOfTasks)
               .orElseThrow(() -> new RuntimeException("TaskGroup not found with id:" + id));
   }
}
