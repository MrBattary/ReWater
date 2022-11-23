package michael.linker.rewater.ui.advanced.devices.model;

import michael.linker.rewater.ui.advanced.devices.enums.RequestStatus;

public class AddPairNewDeviceRequest {
    private final RequestStatus mSuccess;

    public AddPairNewDeviceRequest(final RequestStatus success) {
        mSuccess = success;
    }

    public RequestStatus getStatus() {
        return mSuccess;
    }
}
