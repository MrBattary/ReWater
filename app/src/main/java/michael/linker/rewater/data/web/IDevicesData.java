package michael.linker.rewater.data.web;

import michael.linker.rewater.data.model.DevicesListModel;

public interface IDevicesData {
    /**
     * Get all devices.
     *
     * @return model with list on devices in it
     */
    DevicesListModel getDevicesList();
}
