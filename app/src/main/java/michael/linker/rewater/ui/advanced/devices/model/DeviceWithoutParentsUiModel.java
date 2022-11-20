package michael.linker.rewater.ui.advanced.devices.model;

import michael.linker.rewater.data.model.DetailedStatusModel;
import michael.linker.rewater.data.repository.devices.model.DeviceModel;

public class DeviceWithoutParentsUiModel {
    private final String mId, mName;
    private final DetailedStatusModel mStatus;

    public DeviceWithoutParentsUiModel(
            final String id,
            final String name,
            final DetailedStatusModel status) {
        mId = id;
        mName = name;
        mStatus = status;
    }

    public DeviceWithoutParentsUiModel(final DeviceModel repositoryModel) {
        mId = repositoryModel.getId();
        mName = repositoryModel.getName();
        mStatus = repositoryModel.getStatus();
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
