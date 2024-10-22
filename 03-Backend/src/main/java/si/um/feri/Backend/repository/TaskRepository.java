package si.um.feri.Backend.repository;

import org.springframework.data.repository.CrudRepository;
import si.um.feri.Backend.model.Task;

public interface TaskRepository extends CrudRepository<Task, Integer> {
}
