package michael.linker.rewater.ui.advanced.devices.model;

import michael.linker.rewater.ui.advanced.devices.enums.UiRequestStatus;

public class DeviceUiRequest {
    private final UiRequestStatus mSuccess;

    public DeviceUiRequest(final UiRequestStatus success) {
        mSuccess = success;
    }

    public UiRequestStatus getStatus() {
        return mSuccess;
    }
}
