package michael.linker.rewater.data.web.api.devices.response;

import michael.linker.rewater.data.web.api.part.IdNamePart;
import michael.linker.rewater.data.web.api.part.StatusPart;

public class GetDeviceResponse {
    private final String id;
    private final String name;
    private final StatusPart status;
    private final IdNamePart parentSchedule;
    private final IdNamePart parentNetwork;

    public GetDeviceResponse(String id, String name,
            StatusPart status, IdNamePart parentSchedule,
            IdNamePart parentNetwork) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.parentSchedule = parentSchedule;
        this.parentNetwork = parentNetwork;
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

    public IdNamePart getParentSchedule() {
        return parentSchedule;
    }

    public IdNamePart getParentNetwork() {
        return parentNetwork;
    }
}
