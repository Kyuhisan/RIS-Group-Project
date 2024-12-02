package si.um.feri.Backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class TaskGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String groupName;
    private double groupProgress;

    @OneToMany(mappedBy = "taskGroup", cascade = CascadeType.ALL)
    @JsonManagedReference  // Prevents infinite recursion by serializing the tasks
    private List<Task> listOfTasks;

    public TaskGroup(String groupName, double groupProgress, List<Task> listOfTasks) {
        this.groupName = groupName;
        this.groupProgress = groupProgress;
        this.listOfTasks = (listOfTasks != null) ? listOfTasks : Collections.emptyList();
    }
}
