package si.um.feri.Backend.config;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import si.um.feri.Backend.model.Task;
import si.um.feri.Backend.model.TaskGroup;
import si.um.feri.Backend.model.TaskStatus;
import si.um.feri.Backend.repository.TaskGroupRepository;
import si.um.feri.Backend.repository.TaskRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("DataInitializer Tests")
class DataInitializerTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskGroupRepository taskGroupRepository;

    @InjectMocks
    private DataInitializer dataInitializer;

    @BeforeAll
    static void initAll() {
        System.out.println("Starting DataInitializer tests...");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("Completed DataInitializer tests.");
    }

    @BeforeEach
    void setup() {
        System.out.println("Setting up for a test...");
    }

    @AfterEach
    void cleanup() {
        System.out.println("Cleaning up after a test...");
        Mockito.reset(taskRepository, taskGroupRepository);
    }
    @Nested
    @DisplayName("Positive Tests")
    class PositiveTests{
        @Test
        @DisplayName("Verify TaskGroup Initialization")
        void testTaskGroupInitialization() throws Exception {
            dataInitializer.run();

            verify(taskGroupRepository, times(1)).saveAll(anyList());
        }

        @Test
        @DisplayName("Verify Task Initialization")
        void testTaskInitialization() throws Exception {
            dataInitializer.run();

            verify(taskRepository, times(1)).saveAll(anyList());
        }

        @Test
        @DisplayName("Verify All Tasks Are Saved Correctly")
        void testDataInitializationContent() throws Exception {
            dataInitializer.run();

            List<TaskGroup> expectedGroups = Arrays.asList(
                    new TaskGroup("Homework", 79.36, null),
                    new TaskGroup("Work Tasks", 56.12, null),
                    new TaskGroup("Personal Projects", 45.89, null),
                    new TaskGroup("Home Improvement", 62.73, null),
                    new TaskGroup("Event Planning", 93.47, null)
            );

            List<Task> expectedTasks = Arrays.asList(
                    new Task("Finish Math Assignment", "Complete all exercises from Chapter 5 and submit.", expectedGroups.get(0), TaskStatus.IN_PROGRESS),
                    new Task("Write Essay on History", "Write a 3-page essay about the history of Ancient Egypt.", expectedGroups.get(0), TaskStatus.FINISHED),
                    new Task("Prepare for Chemistry Quiz", "Study chapters 3 and 4 for the upcoming quiz on chemical reactions.", expectedGroups.get(0), TaskStatus.NOT_STARTED),
                    new Task("Complete Quarterly Report", "Finish the financial report for Q3 and send to the manager.", expectedGroups.get(1), TaskStatus.IN_PROGRESS),
                    new Task("Prepare Presentation for Client", "Create a PowerPoint presentation for the client meeting next week.", expectedGroups.get(1), TaskStatus.NOT_STARTED),
                    new Task("Review Code for Bugs", "Go through the codebase and identify any bugs or performance issues.", expectedGroups.get(1), TaskStatus.FINISHED),
                    new Task("Order Wedding Cake", "Order the wedding cake from the local bakery.", expectedGroups.get(4), TaskStatus.NOT_STARTED),
                    new Task("Write Blog Post on Travel Tips", "Research and write a 500-word blog post on travel tips for budget travelers.", expectedGroups.get(2), TaskStatus.IN_PROGRESS),
                    new Task("Create YouTube Video on Cooking", "Record and edit a cooking tutorial video for YouTube.", expectedGroups.get(2), TaskStatus.NOT_STARTED),
                    new Task("Organize Photography Portfolio", "Organize and curate photos for an online portfolio.", expectedGroups.get(2), TaskStatus.FINISHED),
                    new Task("Paint Living Room", "Paint the walls in the living room with a fresh coat of paint.", expectedGroups.get(3), TaskStatus.IN_PROGRESS),
                    new Task("Fix Leaky Faucet", "Repair the leaky faucet in the kitchen sink.", expectedGroups.get(3), TaskStatus.NOT_STARTED),
                    new Task("Assemble Bookshelf", "Assemble the new bookshelf and set it up in the study.", expectedGroups.get(3), TaskStatus.FINISHED),
                    new Task("Install New Lighting", "Install new lighting fixtures in the hallway and bedrooms.", expectedGroups.get(3), TaskStatus.IN_PROGRESS),
                    new Task("Book Venue for Wedding", "Reserve the venue for the wedding ceremony and reception.", expectedGroups.get(4), TaskStatus.FINISHED),
                    new Task("Send Invitations", "Send out wedding invitations to all guests.", expectedGroups.get(4), TaskStatus.IN_PROGRESS)
            );

            verify(taskGroupRepository).saveAll(expectedGroups);
            verify(taskRepository).saveAll(expectedTasks);
        }
    }

    @Nested
    @DisplayName("Negative Tests")
    class NegativeTests {
        @Test
        @DisplayName("Run with Empty Repositories")
        void testEmptyRepositories(){
            doThrow(new RuntimeException("Repository is empty")).when(taskGroupRepository).saveAll(anyList());
            doThrow(new RuntimeException("Repository is empty")).when(taskRepository).saveAll(anyList());

            Assertions.assertThrows(RuntimeException.class, () -> dataInitializer.run());
        }
    }
}
