package michael.linker.rewater.data.repository.schedules;

import java.util.List;

import michael.linker.rewater.data.repository.schedules.model.CompactScheduleModel;

public interface ISchedulesRepository {
    /**
     * Get a simple list of the all schedules.
     *
     * @return the list of compact schedules models in it
     */
    List<CompactScheduleModel> getCompactScheduleList();

    /**
     * Get specific schedule by it's ID.
     *
     * @param id ID of the schedule
     * @return model of the required schedule
     * @throws SchedulesRepositoryNotFoundException if schedule with provided id does not exist
     */
    CompactScheduleModel getCompactScheduleById(String id) throws SchedulesRepositoryNotFoundException;
}
