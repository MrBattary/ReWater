package michael.linker.rewater.data.repository.schedules;

import java.util.List;

import michael.linker.rewater.data.repository.schedules.model.ScheduleModel;

public interface ISchedulesRepository {
    /**
     * Get a simple list of the all schedules.
     *
     * @return the list of compact schedules models in it
     */
    List<ScheduleModel> getCompactScheduleList();

    /**
     * Get specific schedule by it's ID.
     *
     * @param id ID of the schedule
     * @return model of the required schedule
     * @throws SchedulesRepositoryNotFoundException if schedule with provided ID does not exist
     */
    ScheduleModel getCompactScheduleById(String id)
            throws SchedulesRepositoryNotFoundException;

    /**
     * Get schedule ID by inner device ID.
     *
     * @param deviceId inner device ID
     * @return schedule ID
     * @throws SchedulesRepositoryNotFoundException if no schedule contains a device with the
     * provided ID
     */
    String getScheduleIdByIdOfAttachedDevice(String deviceId)
            throws SchedulesRepositoryNotFoundException;
}
