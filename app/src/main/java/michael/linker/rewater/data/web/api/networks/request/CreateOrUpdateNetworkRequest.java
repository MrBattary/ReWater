package michael.linker.rewater.data.web.api.networks.request;

import michael.linker.rewater.data.repository.networks.model.CreateOrUpdateNetworkRepositoryModel;

public class CreateOrUpdateNetworkRequest {
    public final String name;
    public final String description;

    public CreateOrUpdateNetworkRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public CreateOrUpdateNetworkRequest(CreateOrUpdateNetworkRepositoryModel model) {
        name = model.getName();
        description = model.getDescription();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
