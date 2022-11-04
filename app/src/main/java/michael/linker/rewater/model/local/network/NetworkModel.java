package michael.linker.rewater.model.network;

import michael.linker.rewater.constant.Status;
import michael.linker.rewater.model.status.DetailedStatusModel;

public class NetworkModel {
    private final String id;
    private final String heading;
    private final String description;
    private final DetailedStatusModel status;

    public NetworkModel(
            final String heading,
            final String description) {
        this.id = null;
        this.heading = heading;
        this.description = description;
        this.status = new DetailedStatusModel(Status.UNDEFINED, Status.UNDEFINED);
    }

    public NetworkModel(
            final String id,
            final String heading,
            final String description,
            final DetailedStatusModel status) {
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

    public DetailedStatusModel getStatus() {
        return status;
    }
}
