package si.um.feri.Backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
    private boolean status; // true = done, false = not done, null = in progress

    public Task(String taskName, String taskDescription, String taskGroup, boolean status) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskGroup = taskGroup;
        this.status = status;
    }
}
