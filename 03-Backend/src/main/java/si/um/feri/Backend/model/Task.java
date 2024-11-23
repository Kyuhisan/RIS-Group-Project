package si.um.feri.Backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
package si.um.feri.Backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

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
    private TaskGroup taskGroup;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    public Task(String taskName, String taskDescription, TaskGroup taskGroup, TaskStatus status) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskGroup = taskGroup;
        this.status = status;
    }
}