package michael.linker.rewater.data.repository.devices.model;

import michael.linker.rewater.data.model.DetailedStatusModel;

public class DeviceModel {
    private final String mId, mName;
    private final DetailedStatusModel mStatus;

    public DeviceModel(
            final String id,
            final String name,
            final DetailedStatusModel status) {
        mId = id;
        mName = name;
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
}
