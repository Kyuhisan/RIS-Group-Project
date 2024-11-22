package si.um.feri.Backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Task {
    @Id
    @GeneratedValue
    private Integer id;

    private String taskName;
    private String taskDescription;
    private String taskGroup;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    public Task(String taskName, String taskDescription, String taskGroup, TaskStatus status) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskGroup = taskGroup;
        this.status = status;
    }
}