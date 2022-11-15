package michael.linker.rewater.data.repository.devices.model;

public class UpdateDeviceModel {
    private final EditableDeviceModel mDeviceNewModel;
    private final String mNewParentScheduleId;

    public UpdateDeviceModel(
            final EditableDeviceModel deviceNewModel,
            final String newParentScheduleId) {
        mDeviceNewModel = deviceNewModel;
        mNewParentScheduleId = newParentScheduleId;
    }

    public EditableDeviceModel getDeviceNewModel() {
        return mDeviceNewModel;
    }

    public String getNewParentScheduleId() {
        return mNewParentScheduleId;
    }
}
