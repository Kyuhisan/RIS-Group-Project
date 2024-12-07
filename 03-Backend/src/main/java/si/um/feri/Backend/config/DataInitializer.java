package si.um.feri.Backend.config;

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

        TaskGroup group1 = new TaskGroup("Homework", 79.36, null, null, null);
        TaskGroup group2 = new TaskGroup("Work Tasks", 56.12, null, null, null);
        TaskGroup group3 = new TaskGroup("Personal Projects", 45.89, null, null, null);
        TaskGroup group4 = new TaskGroup("Home Improvement", 62.73, null, null, null);
        TaskGroup group5 = new TaskGroup("Event Planning", 93.47, null, null, null);


        taskGroupRepository.saveAll(Arrays.asList(group1, group2, group3, group4, group5));

        taskRepository.saveAll(Arrays.asList(
                new Task("Finish Math Assignment", "Complete all exercises from Chapter 5 and submit.", group1, TaskStatus.IN_PROGRESS),
                new Task("Write Essay on History", "Write a 3-page essay about the history of Ancient Egypt.", group1, TaskStatus.FINISHED),
                new Task("Prepare for Chemistry Quiz", "Study chapters 3 and 4 for the upcoming quiz on chemical reactions.", group1, TaskStatus.NOT_STARTED),
                new Task("Complete Quarterly Report", "Finish the financial report for Q3 and send to the manager.", group2, TaskStatus.IN_PROGRESS),
                new Task("Prepare Presentation for Client", "Create a PowerPoint presentation for the client meeting next week.", group2, TaskStatus.NOT_STARTED),
                new Task("Review Code for Bugs", "Go through the codebase and identify any bugs or performance issues.", group2, TaskStatus.FINISHED),
                new Task("Order Wedding Cake", "Order the wedding cake from the local bakery.", group5, TaskStatus.NOT_STARTED),
                new Task("Write Blog Post on Travel Tips", "Research and write a 500-word blog post on travel tips for budget travelers.", group3, TaskStatus.IN_PROGRESS),
                new Task("Create YouTube Video on Cooking", "Record and edit a cooking tutorial video for YouTube.", group3, TaskStatus.NOT_STARTED),
                new Task("Organize Photography Portfolio", "Organize and curate photos for an online portfolio.", group3, TaskStatus.FINISHED),
                new Task("Paint Living Room", "Paint the walls in the living room with a fresh coat of paint.", group4, TaskStatus.IN_PROGRESS),
                new Task("Fix Leaky Faucet", "Repair the leaky faucet in the kitchen sink.", group4, TaskStatus.NOT_STARTED),
                new Task("Assemble Bookshelf", "Assemble the new bookshelf and set it up in the study.", group4, TaskStatus.FINISHED),
                new Task("Install New Lighting", "Install new lighting fixtures in the hallway and bedrooms.", group4, TaskStatus.IN_PROGRESS),
                new Task("Book Venue for Wedding", "Reserve the venue for the wedding ceremony and reception.", group5, TaskStatus.FINISHED),
                new Task("Send Invitations", "Send out wedding invitations to all guests.", group5, TaskStatus.IN_PROGRESS)
        ));
    }
}