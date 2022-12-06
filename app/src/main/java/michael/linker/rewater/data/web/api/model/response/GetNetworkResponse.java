package michael.linker.rewater.data.web.api.model.response;

import michael.linker.rewater.data.web.api.model.StatusResponsePart;

public class GetNetworkResponse {
    private final String id;
    private final String name;
    private final String description;
    private final StatusResponsePart status;

    public GetNetworkResponse(
            String id,
            String name,
            String description,
            StatusResponsePart status) {
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

    public StatusResponsePart getStatus() {
        return status;
    }
}
