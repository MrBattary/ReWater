package michael.linker.rewater.data.web.api.schedules.response;

import michael.linker.rewater.data.web.api.part.StatusPart;

public class GetScheduleDeviceResponsePart {
    private final String id;
    private final String name;
    private final StatusPart status;

    public GetScheduleDeviceResponsePart(
            String id,
            String name,
            StatusPart status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public StatusPart getStatus() {
        return status;
    }
}
