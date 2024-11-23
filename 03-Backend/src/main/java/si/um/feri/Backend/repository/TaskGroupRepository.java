package si.um.feri.Backend.repository;

import org.springframework.data.repository.CrudRepository;
import si.um.feri.Backend.model.TaskGroup;

public interface TaskGroupRepository extends CrudRepository<TaskGroup, Integer> {
}
