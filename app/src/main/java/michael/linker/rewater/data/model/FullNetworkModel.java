package michael.linker.rewater.data.model;

import michael.linker.rewater.ui.model.DetailedStatusModel;

public class FullNetworkModel {
    private final String mId, mHeading, mDescription;
    private final DetailedStatusModel mStatus;

    public FullNetworkModel(final String id, final NewNetworkModel model) {
        mId = id;
        mHeading = model.getHeading();
        mDescription = model.getDescription();
        mStatus = new DetailedStatusModel(Status.UNDEFINED, Status.UNDEFINED);
    }

    public FullNetworkModel(
            final String id,
            final String heading,
            final String description,
            final DetailedStatusModel status) {
        this.mId = id;
        this.mHeading = heading;
        this.mDescription = description;
        this.mStatus = status;
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
