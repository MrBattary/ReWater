package michael.linker.rewater.data.model;

public class EditableNetworkModel {
    private final String heading, description;

    public EditableNetworkModel(
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
