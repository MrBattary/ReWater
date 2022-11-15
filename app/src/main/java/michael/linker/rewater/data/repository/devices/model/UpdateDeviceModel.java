package michael.linker.rewater.data.repository.devices.model;

public class UpdateDeviceModel extends ShortDeviceModel {
    public UpdateDeviceModel(
            final String deviceId,
            final String newParentScheduleId) {
        super(deviceId, newParentScheduleId);
    }
}
