package michael.linker.rewater.data.repository.schedules;

import java.util.List;

import michael.linker.rewater.data.repository.schedules.model.ScheduleModel;
import michael.linker.rewater.data.repository.schedules.model.ScheduleRepositoryModel;

public interface ISchedulesRepository {
    /**
     * Get a simple list of the all schedules.
     *
     * @return the list of compact schedules models in it
     */
    List<ScheduleModel> getCompactScheduleList();

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
     * @param id ID of the schedule
     * @return model of the required schedule
     * @throws SchedulesRepositoryNotFoundException if schedule with provided ID does not exist
     */
    ScheduleRepositoryModel getScheduleById(String id) throws SchedulesRepositoryNotFoundException;

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
     *                                              provided ID
     */
    String getScheduleIdByIdOfAttachedDevice(String deviceId)
            throws SchedulesRepositoryNotFoundException;
}
