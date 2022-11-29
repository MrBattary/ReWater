package michael.linker.rewater.data.repository.devices;

import java.util.List;

import michael.linker.rewater.data.repository.devices.model.CreateDeviceRepositoryModel;
import michael.linker.rewater.data.repository.devices.model.DeviceIdNameRepositoryModel;
import michael.linker.rewater.data.repository.devices.model.DeviceRepositoryModel;
import michael.linker.rewater.data.repository.devices.model.UpdateDeviceRepositoryModel;

public interface IDevicesRepository {
    /**
     * Get a list of the all devices.
     *
     * @return the list of device models in it
     */
    List<DeviceRepositoryModel> getDeviceList();

    /**
     * Get a list of all devices available to attach to the schedule.
     *
     * @return the list of ID-Name device models in it
     */
    List<DeviceIdNameRepositoryModel> getDeviceAttachList();

    /**
     * Get specific device by it's ID.
     *
     * @param id ID of the device
     * @return model of the required device
     * @throws DevicesRepositoryNotFoundException if device with provided id does not exist
     */
    DeviceRepositoryModel getDeviceById(String id) throws DevicesRepositoryNotFoundException;

    /**
     * Get specific device by it's hardware ID.
     *
     * @param hardwareId hardware ID of the device
     * @return model of the required device
     * @throws DevicesRepositoryNotFoundException if the device with the provided hardware ID
     *                                            does not exist or cannot be accessed
     */
    DeviceRepositoryModel getDeviceByHardware(String hardwareId)
            throws DevicesRepositoryNotFoundException;

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
    void updateDevice(String id, UpdateDeviceRepositoryModel model) throws DevicesRepositoryNotFoundException;

    /**
     * Adds the new device.
     *
     * @param model model with data for creation
     * @throws DevicesRepositoryAlreadyExistsException if the device cannot be added
     */
    void createDevice(CreateDeviceRepositoryModel model) throws DevicesRepositoryAlreadyExistsException;
}
