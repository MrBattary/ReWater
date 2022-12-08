package michael.linker.rewater.data.web.api.devices.request;

public class UpdateDeviceRequest {
    private final String name;
    private final String parentScheduleId;

    public UpdateDeviceRequest(String name, String parentScheduleId) {
        this.name = name;
        this.parentScheduleId = parentScheduleId;
    }

    public String getName() {
        return name;
    }

    public String getParentScheduleId() {
        return parentScheduleId;
    }
}
