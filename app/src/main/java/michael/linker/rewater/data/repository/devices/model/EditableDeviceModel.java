package michael.linker.rewater.data.repository.devices.model;

public class EditableDeviceModel {
    private final String mName;
    private final String mParentScheduleId, mParentNetworkId;

    public EditableDeviceModel(
            final String name,
            final String parentScheduleId,
            final String parentNetworkId) {
        mName = name;
        mParentScheduleId = parentScheduleId;
        mParentNetworkId = parentNetworkId;
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
}
