package michael.linker.rewater.data.web.api.devices.request;

public class CreateDeviceRequest {
    private final String name;
    private final String parentScheduleId;
    private final String deviceHardcodedId;

    public CreateDeviceRequest(
            String name,
            String parentScheduleId,
            String deviceHardcodedId) {
        this.name = name;
        this.parentScheduleId = parentScheduleId;
        this.deviceHardcodedId = deviceHardcodedId;
    }

    public String getName() {
        return name;
    }

    public String getParentScheduleId() {
        return parentScheduleId;
    }

    public String getDeviceHardcodedId() {
        return deviceHardcodedId;
    }
}
