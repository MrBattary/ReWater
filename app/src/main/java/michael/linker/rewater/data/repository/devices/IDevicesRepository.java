package michael.linker.rewater.data.repository.devices;

import java.util.List;

import michael.linker.rewater.data.repository.devices.model.CompactDeviceModel;

public interface IDevicesRepository {
    /**
     * Get a simple list of the all devices.
     *
     * @return the list of compact device models in it
     */
    List<CompactDeviceModel> getCompactDeviceList();
}
