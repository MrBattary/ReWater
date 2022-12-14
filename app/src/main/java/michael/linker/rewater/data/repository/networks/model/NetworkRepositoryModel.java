package michael.linker.rewater.data.repository.networks.model;

import michael.linker.rewater.data.model.status.DetailedStatusModel;
import michael.linker.rewater.data.web.api.networks.response.GetNetworkResponse;

public class NetworkRepositoryModel {
    private final String mId, mName, mDescription;
    private final DetailedStatusModel mStatus;

    public NetworkRepositoryModel(
            final String id,
            final String name,
            final String description,
            final DetailedStatusModel status) {
        mId = id;
        mName = name;
        mDescription = description;
        mStatus = status;
    }

    public NetworkRepositoryModel(final GetNetworkResponse response) {
        mId = response.getId();
        mName = response.getName();
        mDescription = response.getDescription();
        mStatus = new DetailedStatusModel(response.getStatus());
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

    public DetailedStatusModel getStatus() {
        return mStatus;
    }
}
