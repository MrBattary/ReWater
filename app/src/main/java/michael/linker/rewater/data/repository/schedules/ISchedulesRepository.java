package michael.linker.rewater.data.repository.schedules;

import java.util.List;

import michael.linker.rewater.data.repository.schedules.model.CreateOrUpdateScheduleRepositoryModel;
import michael.linker.rewater.data.repository.schedules.model.ScheduleRepositoryModel;

public interface ISchedulesRepository {
    /**
     * Get a list of the all schedules.
     *
     * @return the list of all schedules models or an empty list
     */
    List<ScheduleRepositoryModel> getAllSchedules();

    /**
     * Get a list of the all schedules by parent network ID.
     *
     * @param networkId the parent network ID
     * @return the list of schedules models in the network with the provided ID or an empty list
     */
    List<ScheduleRepositoryModel> getScheduleListByNetworkId(String networkId);

    /**
     * Get specific schedule by it's ID.
     *
     * @param scheduleId ID of the schedule
     * @return model of the required schedule
     * @throws SchedulesRepositoryNotFoundException if schedule with provided ID does not exist
     */
    ScheduleRepositoryModel getScheduleById(String scheduleId)
            throws SchedulesRepositoryNotFoundException;

    /**
     * Create specific schedule in the specific network using repository model.
     *
     * @param networkId parent network ID
     * @param model     repository model
     * @throws SchedulesRepositoryAlreadyExistsException if the schedule cannot be created
     */
    void createSchedule(String networkId, CreateOrUpdateScheduleRepositoryModel model)
            throws SchedulesRepositoryAlreadyExistsException;

    /**
     * Update specific schedule in using repository model. The network will remain the same.
     *
     * @param scheduleId ID of the schedule to be updated
     * @param model      repository model
     * @throws SchedulesRepositoryAlreadyExistsException if the schedule was not found
     */
    void updateSchedule(String scheduleId, CreateOrUpdateScheduleRepositoryModel model)
            throws SchedulesRepositoryNotFoundException;

    /**
     * Remove an existing schedule.
     *
     * @param scheduleId ID of the schedule to be removed
     */
    void removeSchedule(String scheduleId);
}
