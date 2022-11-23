package michael.linker.rewater.data.repository.networks.model;

public class CreateOrUpdateNetworkRepositoryModel {
    private final String heading, description;

    public CreateOrUpdateNetworkRepositoryModel(
            final String heading,
            final String description) {
        this.heading = heading;
        this.description = description;
    }

    public String getHeading() {
        return heading;
    }

    public String getDescription() {
        return description;
    }
}
