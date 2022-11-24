package michael.linker.rewater.data.local.stub;

import java.util.List;

import michael.linker.rewater.data.local.stub.model.FullDeviceModel;

public interface IDevicesData {
    /**
     * Get all devices.
     *
     * @return model with list on devices in it
     */
    List<FullDeviceModel> getDevicesList();

    /**
     * Get specific device by it's id.
     *
     * @param id ID of the device
     * @return model of the required device
     */
    FullDeviceModel getDeviceById(String id);

    void removeDeviceById(String id);

    /**
     * Update an existing device.
     *
     * @param id ID of the device to be updated
     * @param model model with data for update
     */
    void updateDevice(String id, FullDeviceModel model);

    void addDevice(FullDeviceModel model);
}
