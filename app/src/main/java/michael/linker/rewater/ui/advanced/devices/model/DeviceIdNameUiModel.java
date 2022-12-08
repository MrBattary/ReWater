package michael.linker.rewater.ui.advanced.devices.model;

import michael.linker.rewater.data.repository.devices.model.DeviceRepositoryModel;

public class DeviceIdNameUiModel {
    private final String mId, mName;

    public DeviceIdNameUiModel(
            final String id,
            final String name) {
        mId = id;
        mName = name;
    }

    public DeviceIdNameUiModel(DeviceRepositoryModel repositoryModel) {
        mId = repositoryModel.getId();
        mName = repositoryModel.getName();
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }
}
