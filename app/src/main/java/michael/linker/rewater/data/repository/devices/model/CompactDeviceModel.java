package michael.linker.rewater.data.repository.devices.model;

import androidx.annotation.Nullable;

import michael.linker.rewater.data.model.IdNameModel;
import michael.linker.rewater.ui.model.DetailedStatusModel;

public class CompactDeviceModel {
    private final String mId, mName;
    private final IdNameModel mParentSchedule;
    private final IdNameModel mParentNetwork;
    private final DetailedStatusModel mStatus;

    public CompactDeviceModel(
            final String id,
            final String name,
            @Nullable final IdNameModel parentSchedule,
            @Nullable final IdNameModel parentNetwork,
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

    public IdNameModel getParentSchedule() {
        return mParentSchedule;
    }

    public IdNameModel getParentNetwork() {
        return mParentNetwork;
    }

    public DetailedStatusModel getStatus() {
        return mStatus;
    }
}
