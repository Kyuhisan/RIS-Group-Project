package si.um.feri.Backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    @Lob
    private byte[] fileBlob;
    private String fileName;

    private LocalDate creationDate;
    private String period;

    public TaskGroup(String groupName, double groupProgress, List<Task> listOfTasks, byte[] fileBlob, String fileName) {
        this.groupName = groupName;
        this.groupProgress = groupProgress;
        this.listOfTasks = (listOfTasks != null) ? listOfTasks : Collections.emptyList();
        this.fileBlob = fileBlob;
        this.fileName = fileName;
        this.creationDate = LocalDate.now();
        this.period = null;
    }
}