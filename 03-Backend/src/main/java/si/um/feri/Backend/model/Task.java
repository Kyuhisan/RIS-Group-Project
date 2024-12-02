package si.um.feri.Backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String taskName;
    private String taskDescription;

    @ManyToOne
    @JoinColumn(name = "taskGroup_id")
    @JsonBackReference  // Prevents infinite recursion when serializing TaskGroup
    private TaskGroup taskGroup;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    public Task(String taskName, String taskDescription, TaskGroup taskGroup, TaskStatus status) {
        if (taskName == null || taskName.isEmpty()) {
            throw new IllegalArgumentException("Task name cannot be null or empty");
        }
        if (status == null) {
            throw new IllegalArgumentException("Task status cannot be null");
        }
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskGroup = taskGroup;
        this.status = status;
    }
}
