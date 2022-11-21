package michael.linker.rewater.data.repository.devices.model;

public class DeviceIdNameRepositoryModel {
    private final String mId, mName;

    public DeviceIdNameRepositoryModel(
            final String id,
            final String name) {
        mId = id;
        mName = name;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }
}
