package michael.linker.rewater.data.model;

import michael.linker.rewater.ui.model.DetailedStatusModel;

public class FullDeviceModel {
    private final String mId, mName, mParentScheduleId, mParentNetworkId;
    private final DetailedStatusModel mStatus;

    public FullDeviceModel(
            final String id,
            final String name,
            final String parentScheduleId,
            final String parentNetworkId,
            final DetailedStatusModel status) {
        mId = id;
        mName = name;
        mParentScheduleId = parentScheduleId;
        mParentNetworkId = parentNetworkId;
        mStatus = status;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getParentScheduleId() {
        return mParentScheduleId;
    }

    public String getParentNetworkId() {
        return mParentNetworkId;
    }

    public DetailedStatusModel getStatus() {
        return mStatus;
    }
}
