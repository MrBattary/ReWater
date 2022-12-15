package michael.linker.rewater.data.web.api.devices.request;

import michael.linker.rewater.data.repository.devices.model.UpdateDeviceRepositoryModel;

public class UpdateDeviceRequest {
    private final String name;
    private final String parentScheduleId;

    public UpdateDeviceRequest(String name, String parentScheduleId) {
        this.name = name;
        this.parentScheduleId = parentScheduleId;
    }

    public UpdateDeviceRequest(UpdateDeviceRepositoryModel model) {
        name = model.getName();
        parentScheduleId = model.getScheduleId();
    }

    public String getName() {
        return name;
    }

    public String getParentScheduleId() {
        return parentScheduleId;
    }
}
