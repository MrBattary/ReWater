package michael.linker.rewater.model.network;

import michael.linker.rewater.model.status.StatusModel;

public class NetworkItemModel {
    private final String id;
    private final String heading;
    private final String description;
    private final StatusModel status;

    public NetworkItemModel(
            final String id,
            final String heading,
            final String description,
            final StatusModel status) {
        this.id = id;
        this.heading = heading;
        this.description = description;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getHeading() {
        return heading;
    }

    public String getDescription() {
        return description;
    }

    public StatusModel getStatus() {
        return status;
    }
}
