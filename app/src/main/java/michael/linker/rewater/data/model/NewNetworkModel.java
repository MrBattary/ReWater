package michael.linker.rewater.data.model;

public class NewNetworkModel {
    private final String heading, description;

    public NewNetworkModel(
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
