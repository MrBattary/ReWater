package michael.linker.rewater.data.repository.devices;

import java.util.List;

import michael.linker.rewater.data.repository.devices.model.CompactDeviceModel;
import michael.linker.rewater.data.repository.devices.model.UpdateDeviceModel;

public interface IDevicesRepository {
    /**
     * Get a simple list of the all devices.
     *
     * @return the list of compact device models in it
     */
    List<CompactDeviceModel> getCompactDeviceList();

    /**
     * Get specific device by it's ID.
     *
     * @param id ID of the device
     * @return model of the required device
     * @throws DevicesRepositoryNotFoundException if device with provided id does not exist
     */
    CompactDeviceModel getCompactNetworkById(String id) throws DevicesRepositoryNotFoundException;

    /**
     * Remove an existing device from the user device list.
     *
     * @param id ID of the device to be removed
     */
    void removeDevice(String id);

    /**
     * Update an existing device.
     *
     * @param id    ID of the device to be updated
     * @param model model with data for update
     * @throws DevicesRepositoryNotFoundException if device with provided id does not exist
     */
    void updateDevice(String id, UpdateDeviceModel model) throws DevicesRepositoryNotFoundException;
}
