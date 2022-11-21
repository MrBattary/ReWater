package michael.linker.rewater.data.repository.devices;

import java.util.List;

import michael.linker.rewater.data.repository.devices.model.AddDeviceModel;
import michael.linker.rewater.data.repository.devices.model.DeviceIdNameRepositoryModel;
import michael.linker.rewater.data.repository.devices.model.DeviceModel;
import michael.linker.rewater.data.repository.devices.model.DeviceHardwareModel;
import michael.linker.rewater.data.repository.devices.model.UpdateDeviceModel;

public interface IDevicesRepository {
    /**
     * Get a simple list of the all devices.
     *
     * @return the list of device models in it
     */
    List<DeviceModel> getDeviceList();

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
    DeviceModel getDeviceById(String id) throws DevicesRepositoryNotFoundException;

    /**
     * Get specific device by it's hardware ID.
     *
     * @param model hardware model that contains hardware ID
     * @return model of the required device
     * @throws DevicesRepositoryNotFoundException if the device with the provided hardware ID
     *                                            does not exist or cannot be accessed
     */
    DeviceModel getDeviceByHardware(DeviceHardwareModel model)
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
    void updateDevice(String id, UpdateDeviceModel model) throws DevicesRepositoryNotFoundException;

    /**
     * Adds the new device.
     *
     * @param model model with data for adding
     * @throws DevicesRepositoryAlreadyExistsException if the device cannot be added
     */
    void addNewDevice(AddDeviceModel model) throws DevicesRepositoryAlreadyExistsException;
}
