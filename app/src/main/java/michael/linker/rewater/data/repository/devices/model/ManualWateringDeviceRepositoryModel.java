package michael.linker.rewater.data.repository.devices.model;

import michael.linker.rewater.data.model.unit.WaterVolumeMetricModel;

public class ManualWateringDeviceRepositoryModel {
    private final WaterVolumeMetricModel mVolumeModel;
    private final boolean isForceWatering;

    public ManualWateringDeviceRepositoryModel(
            WaterVolumeMetricModel volumeModel,
            boolean isForceWatering) {
        mVolumeModel = volumeModel;
        this.isForceWatering = isForceWatering;
    }

    public WaterVolumeMetricModel getVolumeModel() {
        return mVolumeModel;
    }

    public boolean isForceWatering() {
        return isForceWatering;
    }
}
