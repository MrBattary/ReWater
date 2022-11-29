package michael.linker.rewater.data.local.stub.model;

public class FullNetworkModel {
    private final String mId, mName, mDescription;

    public FullNetworkModel(
            final String id,
            final String name,
            final String description) {
        mId = id;
        mName = name;
        mDescription = description;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }
}
