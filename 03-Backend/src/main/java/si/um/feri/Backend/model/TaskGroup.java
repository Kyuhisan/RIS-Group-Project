package si.um.feri.Backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class TaskGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer groupId;

    private String groupName;
    private double groupProgress;

    @OneToMany(mappedBy = "taskGroup")
    private List<Task> listOfTasks;

    public TaskGroup(String groupName, double groupProgress, List<Task> listOfTasks) {
        this.groupName = groupName;
        this.groupProgress = groupProgress;
        this.listOfTasks = listOfTasks;
    }
}