package michael.linker.rewater.data.repository.devices.model;

import michael.linker.rewater.data.model.IdNameModel;

public class DeviceIdNameRepositoryModel {
    private final IdNameModel mIdNameModel;

    public DeviceIdNameRepositoryModel(
            final String id,
            final String name) {
        mIdNameModel = new IdNameModel(id, name);
    }

    public String getId() {
        return mIdNameModel.getId();
    }

    public String getName() {
        return mIdNameModel.getName();
    }
}
