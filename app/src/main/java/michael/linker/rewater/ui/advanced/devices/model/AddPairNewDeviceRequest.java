package michael.linker.rewater.ui.advanced.devices.model;

public class AddPairNewDeviceRequest {
    private final RequestStatus mSuccess;

    public AddPairNewDeviceRequest(final RequestStatus success) {
        mSuccess = success;
    }

    public RequestStatus getStatus() {
        return mSuccess;
    }
}
