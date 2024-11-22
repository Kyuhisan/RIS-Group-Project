package si.um.feri.Backend.repository;

import org.springframework.data.repository.CrudRepository;
import si.um.feri.Backend.model.Group;

public interface GroupRepository extends CrudRepository<Group, Integer> {
}
