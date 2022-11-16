package michael.linker.rewater.ui.advanced.devices.model;

import androidx.annotation.Nullable;

import michael.linker.rewater.data.model.IdNameModel;
import michael.linker.rewater.data.model.DetailedStatusModel;

public class DeviceCardModel {
    private final String mId, mName;
    private final IdNameModel mParentSchedule;
    private final IdNameModel mParentNetwork;
    private final DetailedStatusModel mStatus;

    public DeviceCardModel(
            final String id,
            final String name,
            @Nullable IdNameModel parentSchedule,
            @Nullable IdNameModel parentNetwork,
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
