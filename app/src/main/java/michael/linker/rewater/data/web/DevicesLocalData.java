package michael.linker.rewater.data.web;

import java.util.ArrayList;
import java.util.List;

import michael.linker.rewater.data.web.model.DevicesListModel;
import michael.linker.rewater.data.web.model.FullDeviceModel;
import michael.linker.rewater.data.model.Status;
import michael.linker.rewater.ui.model.DetailedStatusModel;

public class DevicesLocalData implements IDevicesData {
    private final List<FullDeviceModel> mFullDeviceModels;

    public DevicesLocalData() {
        mFullDeviceModels = new ArrayList<>();
        mFullDeviceModels.add(new FullDeviceModel(
                "a2726b77-2214-4aa2-b4af-2fac0a4f84bc",
                "First device",
                new DetailedStatusModel(Status.OK, Status.OK)));
        mFullDeviceModels.add(new FullDeviceModel(
                "832b9239-b362-47c1-bfbc-96589bcad643",
                "Second device",
                new DetailedStatusModel(Status.OK, Status.WARNING)));
        mFullDeviceModels.add(new FullDeviceModel(
                "13fea3f4-b6dc-4ec9-bba7-4e1bef39157f",
                "Third device",
                new DetailedStatusModel(Status.WARNING, Status.DEFECT)));
        mFullDeviceModels.add(new FullDeviceModel(
                "794fa505-1cdc-480e-b198-e062a36e8fa7",
                "Fourth device",
                new DetailedStatusModel(Status.DEFECT, Status.UNDEFINED)));
        mFullDeviceModels.add(new FullDeviceModel(
                "353f5967-c7bd-4c57-8528-4f1ab7b88005",
                "Fifth schedule very very very very very long name",
                new DetailedStatusModel(Status.UNDEFINED, Status.UNDEFINED)));
    }

    @Override
    public DevicesListModel getDevicesList() {
        return new DevicesListModel(mFullDeviceModels);
    }

    @Override
    public FullDeviceModel getDeviceById(final String id) {
        for (FullDeviceModel deviceModel : mFullDeviceModels) {
            if (id.equals(deviceModel.getId())) {
                return deviceModel;
            }
        }
        return null;
    }

    @Override
    public void removeDeviceById(final String id) {
        for (int i = 0; i < mFullDeviceModels.size(); i++) {
            if (id.equals(mFullDeviceModels.get(i).getId())) {
                mFullDeviceModels.remove(i);
                return;
            }
        }
    }
}
