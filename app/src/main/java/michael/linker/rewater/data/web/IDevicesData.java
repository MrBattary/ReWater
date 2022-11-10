package michael.linker.rewater.data.web;

import michael.linker.rewater.data.web.model.DevicesListModel;
import michael.linker.rewater.data.web.model.FullDeviceModel;

public interface IDevicesData {
    /**
     * Get all devices.
     *
     * @return model with list on devices in it
     */
    DevicesListModel getDevicesList();

    /**
     * Get specific device by it's id.
     *
     * @param id ID of the device
     * @return model of the required device
     */
    FullDeviceModel getDeviceById(String id);
}
