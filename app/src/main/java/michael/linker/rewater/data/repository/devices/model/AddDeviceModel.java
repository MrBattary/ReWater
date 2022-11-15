package michael.linker.rewater.data.repository.devices.model;

public class AddDeviceModel extends UpdateDeviceModel {
    private final String mDeviceHardwareId;

    public AddDeviceModel(
            final EditableDeviceModel deviceNewModel,
            final String parentScheduleId,
            final String deviceHardwareId) {
        super(deviceNewModel, parentScheduleId);
        mDeviceHardwareId = deviceHardwareId;
    }

    public String getDeviceHardwareId() {
        return mDeviceHardwareId;
    }
}
