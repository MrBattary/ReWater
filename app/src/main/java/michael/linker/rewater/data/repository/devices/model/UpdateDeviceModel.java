package michael.linker.rewater.data.repository.devices.model;

public class UpdateDeviceModel {
    private final EditableDeviceModel mDeviceNewModel;
    private final String mParentScheduleNewId;

    public UpdateDeviceModel(
            final EditableDeviceModel deviceNewModel,
            final String parentScheduleNewId) {
        mDeviceNewModel = deviceNewModel;
        mParentScheduleNewId = parentScheduleNewId;
    }

    public EditableDeviceModel getDeviceNewModel() {
        return mDeviceNewModel;
    }

    public String getParentScheduleNewId() {
        return mParentScheduleNewId;
    }
}
