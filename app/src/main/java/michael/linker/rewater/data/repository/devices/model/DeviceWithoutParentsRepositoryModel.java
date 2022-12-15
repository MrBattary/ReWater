package michael.linker.rewater.data.repository.devices.model;

import michael.linker.rewater.data.model.status.DetailedStatusModel;
import michael.linker.rewater.data.web.api.schedules.response.GetScheduleDeviceResponsePart;

public class DeviceWithoutParentsRepositoryModel {
    private final String mId, mName;
    private final DetailedStatusModel mStatus;

    public DeviceWithoutParentsRepositoryModel(
            final String id,
            final String name,
            final DetailedStatusModel status) {
        mId = id;
        mName = name;
        mStatus = status;
    }

    public DeviceWithoutParentsRepositoryModel(
            final GetScheduleDeviceResponsePart deviceResponsePart) {
        mId = deviceResponsePart.getId();
        mName = deviceResponsePart.getName();
        mStatus = new DetailedStatusModel(deviceResponsePart.getStatus());
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public DetailedStatusModel getStatus() {
        return mStatus;
    }
}
