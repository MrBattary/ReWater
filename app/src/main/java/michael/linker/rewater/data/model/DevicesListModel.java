package michael.linker.rewater.data.model;

import java.util.List;

public class DevicesListModel {
    private final List<FullDeviceModel> mFullDeviceModels;

    public DevicesListModel(
            List<FullDeviceModel> fullDeviceModels) {
        mFullDeviceModels = fullDeviceModels;
    }

    public List<FullDeviceModel> getFullDeviceModels() {
        return mFullDeviceModels;
    }
}
