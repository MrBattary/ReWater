package michael.linker.rewater.data.web.api.networks.response;

import michael.linker.rewater.data.web.api.common.part.StatusPart;

public class GetNetworkResponse {
    private final String id;
    private final String name;
    private final String description;
    private final StatusPart status;

    public GetNetworkResponse(
            String id,
            String name,
            String description,
            StatusPart status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public StatusPart getStatus() {
        return status;
    }
}
