package michael.linker.rewater.data.local.stub.model;

import michael.linker.rewater.data.model.status.DetailedStatusModel;

public class FullDeviceModel {
    private final String mId, mName;
    private final DetailedStatusModel mStatus;

    public FullDeviceModel(
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
