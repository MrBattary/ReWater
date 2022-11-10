package michael.linker.rewater.data.web;

import java.util.ArrayList;
import java.util.List;

import michael.linker.rewater.data.model.DevicesListModel;
import michael.linker.rewater.data.model.FullDeviceModel;

public class DevicesLocalData implements IDevicesData {
    private final List<FullDeviceModel> mFullDeviceModels;

    public DevicesLocalData() {
        mFullDeviceModels = new ArrayList<>();
    }

    @Override
    public DevicesListModel getDevicesList() {
        return new DevicesListModel(mFullDeviceModels);
    }
}
