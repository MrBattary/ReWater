package michael.linker.rewater.data.local.stub;

import java.util.List;

import michael.linker.rewater.data.local.stub.model.FullScheduleModel;

public interface ISchedulesData {
    /**
     * Get all schedules.
     *
     * @return model with list on schedules in it
     */
    List<FullScheduleModel> getScheduleList();

    /**
     * Get specific schedule by it's id.
     *
     * @param id ID of the schedule
     * @return model of the required schedule
     */
    FullScheduleModel getScheduleById(String id);

    void updateSchedule(String id, FullScheduleModel newModel);

    void createSchedule(FullScheduleModel newModel);

    void removeSchedule(String id);
}
