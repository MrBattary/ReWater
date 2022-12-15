package michael.linker.rewater.data.repository.networks.model;

public class CreateOrUpdateNetworkRepositoryModel {
    private final String mName, mDescription;

    public CreateOrUpdateNetworkRepositoryModel(
            final String name,
            final String description) {
        this.mName = name;
        this.mDescription = description;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }
}
