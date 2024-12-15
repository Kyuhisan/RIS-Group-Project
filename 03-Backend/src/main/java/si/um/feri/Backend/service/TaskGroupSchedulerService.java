package si.um.feri.Backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import si.um.feri.Backend.model.Period;
import si.um.feri.Backend.model.TaskGroup;
import si.um.feri.Backend.repository.TaskGroupRepository;

import java.time.LocalDate;

@Service
public class TaskGroupSchedulerService {
    @Autowired
    private TaskGroupRepository taskGroupRepository;
    @Autowired
    private TaskGroupService taskGroupService;

    private LocalDate calculateNextCreationDate(LocalDate creationDate, Period period) {
        return switch (period) {
            case DAILY -> creationDate.plusDays(1);
            case WEEKLY -> creationDate.plusWeeks(1);
            case MONTHLY -> creationDate.plusMonths(1);
        };
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void taskGroupScheduler() {
        Iterable<TaskGroup> taskGroups = taskGroupRepository.findAll();

        for (TaskGroup taskGroup : taskGroups) {
            if(taskGroup.getPeriod() != null && taskGroup.getCreationDate() != null) {
                LocalDate nextCreationDate = calculateNextCreationDate(taskGroup.getCreationDate(), taskGroup.getPeriod());

                if(!nextCreationDate.isAfter(LocalDate.now())){
                    TaskGroup dupTaskGroup = taskGroupService.duplicateTaskGroup(taskGroup);
                    dupTaskGroup.setCreationDate(nextCreationDate);
                    taskGroupRepository.save(dupTaskGroup);
                }
            }
        }
    }
}
