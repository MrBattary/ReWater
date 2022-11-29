package michael.linker.rewater.ui.advanced.networks.model;

import michael.linker.rewater.data.model.status.DetailedStatusModel;
import michael.linker.rewater.data.repository.networks.model.NetworkRepositoryModel;

public class NetworkUiModel {
    private final String mId, mName, mDescription;
    private final DetailedStatusModel mStatus;

    public NetworkUiModel(
            final String id,
            final String name,
            final String description,
            final DetailedStatusModel status) {
        mId = id;
        mName = name;
        mDescription = description;
        mStatus = status;
    }

    public NetworkUiModel(final NetworkRepositoryModel model) {
        mId = model.getId();
        mName = model.getName();
        mDescription = model.getDescription();
        mStatus = model.getStatus();
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
