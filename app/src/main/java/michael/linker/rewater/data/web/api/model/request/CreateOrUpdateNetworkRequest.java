package michael.linker.rewater.data.web.api.model.request;

public class CreateOrUpdateNetworkRequest {
    public final String name;
    public final String description;

    public CreateOrUpdateNetworkRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
