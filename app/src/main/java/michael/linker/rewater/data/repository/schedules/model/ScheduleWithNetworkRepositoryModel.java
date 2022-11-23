package michael.linker.rewater.data.repository.schedules.model;

import michael.linker.rewater.data.model.IdNameModel;

public class ScheduleWithNetworkRepositoryModel {
    private final IdNameModel mScheduleIdNameModel, mParentNetworkIdNameModel;

    public ScheduleWithNetworkRepositoryModel(
            final String scheduleId,
            final String scheduleName,
            final String parentNetworkId,
            final String parentNetworkName) {
        mScheduleIdNameModel = new IdNameModel(scheduleId, scheduleName);
        mParentNetworkIdNameModel = new IdNameModel(parentNetworkId, parentNetworkName);
    }

    public IdNameModel getScheduleIdNameModel() {
        return mScheduleIdNameModel;
    }

    public IdNameModel getParentNetworkIdNameModel() {
        return mParentNetworkIdNameModel;
    }
}
