package michael.linker.rewater.data.repository.devices.model;

import michael.linker.rewater.data.model.IdNameModel;
import michael.linker.rewater.data.model.status.DetailedStatusModel;
import michael.linker.rewater.data.web.api.devices.response.GetDeviceResponse;

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

    public DeviceRepositoryModel(GetDeviceResponse response) {
        mId = response.getId();
        mName = response.getName();
        mParentSchedule = new IdNameModel(response.getParentSchedule());
        mParentNetwork = new IdNameModel(response.getParentNetwork());
        mStatus = new DetailedStatusModel(response.getStatus());
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
