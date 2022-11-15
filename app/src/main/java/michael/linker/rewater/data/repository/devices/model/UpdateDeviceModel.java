package michael.linker.rewater.data.repository.devices.model;

public class UpdateDeviceModel extends ShortDeviceModel {
    public UpdateDeviceModel(
            final String deviceName,
            final String newParentScheduleId) {
        super(deviceName, newParentScheduleId);
    }
}
