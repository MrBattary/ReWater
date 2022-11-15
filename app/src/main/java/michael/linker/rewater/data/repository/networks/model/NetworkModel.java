package michael.linker.rewater.data.repository.networks.model;

import michael.linker.rewater.ui.model.DetailedStatusModel;

public class NetworkModel {
    private final String mId, mHeading, mDescription;
    private final DetailedStatusModel mStatus;

    public NetworkModel(
            final String id,
            final String heading,
            final String description,
            final DetailedStatusModel status) {
        mId = id;
        mHeading = heading;
        mDescription = description;
        mStatus = status;
    }

    public String getId() {
        return mId;
    }

    public String getHeading() {
        return mHeading;
    }

    public String getDescription() {
        return mDescription;
    }

    public DetailedStatusModel getStatus() {
        return mStatus;
    }
}
