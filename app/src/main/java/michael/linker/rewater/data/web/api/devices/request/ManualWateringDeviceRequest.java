package michael.linker.rewater.data.web.api.devices.request;

import michael.linker.rewater.data.repository.devices.model.ManualWateringDeviceRepositoryModel;
import michael.linker.rewater.data.web.api.part.VolumePart;

public class ManualWateringDeviceRequest extends VolumePart {

    public ManualWateringDeviceRequest(Integer l, Integer ml) {
        super(l, ml);
    }

    public ManualWateringDeviceRequest(ManualWateringDeviceRepositoryModel model) {
        super(model.getVolumeModel().getLitres(), model.getVolumeModel().getMillilitres());
    }
}
