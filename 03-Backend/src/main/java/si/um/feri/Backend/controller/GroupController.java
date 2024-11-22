package si.um.feri.Backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import si.um.feri.Backend.model.Task;
import si.um.feri.Backend.repository.GroupRepository;
import org.springframework.web.bind.annotation.*;
import si.um.feri.Backend.model.Group;

import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin("http://localhost:5173")
public class GroupController {
    Logger logger = Logger.getLogger(GroupController.class.getName());

    @Autowired
    GroupRepository groupRepository;

    @GetMapping("/groups")
    public Iterable<Group> getAllGroups() {
        logger.info("Getting all groups");
        return groupRepository.findAll();
    }

    @GetMapping("/group/{id}")
    Group getGroupById(@PathVariable int id) {
        logger.info("Getting group with id:" + id);
        return groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id:" + id));
    }

    @PutMapping("/group/{id}")
    Group updateGroup(@RequestBody Group newGroup, @PathVariable int id) {
        logger.info("Updating group with id:" + id);
        return groupRepository.findById(id).map(
                group -> {
                    group.setGroupName(newGroup.getGroupName());
                    group.setGroupProgress(newGroup.getGroupProgress());
                    group.setListOfTasks(newGroup.getListOfTasks());
                    return groupRepository.save(group);
                }).orElseThrow(() -> new RuntimeException("Group not found with id:" + id));
    }

    @DeleteMapping("/group/{id}")
    String deleteGroup(@PathVariable int id) {
        logger.info("Deleting group with id:" + id);
        groupRepository.deleteById(id);
        return "Deleted group with id:" + id;
    }

    @PostMapping("/groups")
    Group addGroup(@RequestBody Group newGroup) {
        logger.info("Adding group with name:" + newGroup.getGroupName());
        return groupRepository.save(newGroup);
    }

    @GetMapping("/group/{id}")
    public List<Task> getAllGroupTasks(@PathVariable int id) {
        logger.info("Getting all group tasks");
        return groupRepository.findById(id)
                .map(Group::getListOfTasks)
                .orElseThrow(() -> new RuntimeException("Group not found with id:" + id));
    }

}
