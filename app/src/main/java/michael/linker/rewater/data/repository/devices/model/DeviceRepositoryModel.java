package michael.linker.rewater.data.repository.devices.model;

import michael.linker.rewater.data.model.IdNameModel;
import michael.linker.rewater.data.model.status.DetailedStatusModel;

public class DeviceRepositoryModel {
    private final String mId, mName;
    private final IdNameModel mParentSchedule, mParentNetwork;
    private final DetailedStatusModel mStatus;

    public DeviceRepositoryModel(
            final String id,
            final String name,
            final IdNameModel parentSchedule,
            final IdNameModel parentNetwork,
            final DetailedStatusModel status) {
        mId = id;
        mName = name;
        mParentSchedule = parentSchedule;
        mParentNetwork = parentNetwork;
        mStatus = status;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public DetailedStatusModel getStatus() {
        return mStatus;
    }

    public IdNameModel getParentSchedule() {
        return mParentSchedule;
    }

    public IdNameModel getParentNetwork() {
        return mParentNetwork;
    }
}
