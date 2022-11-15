package michael.linker.rewater.data.repository.networks.model;

public class ShortNetworkModel {
    private final String heading, description;

    public ShortNetworkModel(
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
