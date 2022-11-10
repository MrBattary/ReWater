package michael.linker.rewater.data.web;

import michael.linker.rewater.data.web.model.FullScheduleModel;
import michael.linker.rewater.data.web.model.ScheduleListModel;

public interface ISchedulesData {
    /**
     * Get all schedules.
     *
     * @return model with list on schedules in it
     */
    ScheduleListModel getScheduleList();

    /**
     * Get specific schedule by it's id.
     *
     * @param id ID of the schedule
     * @return model of the required schedule
     */
    FullScheduleModel getScheduleById(String id);
}
