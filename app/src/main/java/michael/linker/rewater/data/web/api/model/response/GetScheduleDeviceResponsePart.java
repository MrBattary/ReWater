package michael.linker.rewater.data.web.api.model.response;

import michael.linker.rewater.data.web.api.model.StatusResponsePart;

public class GetScheduleDeviceResponsePart {
    private final String id;
    private final String name;
    private final StatusResponsePart status;

    public GetScheduleDeviceResponsePart(
            String id,
            String name,
            StatusResponsePart status) {
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

    public StatusResponsePart getStatus() {
        return status;
    }
}
