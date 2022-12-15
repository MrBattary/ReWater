package michael.linker.rewater.ui.advanced.devices.model;

import michael.linker.rewater.ui.advanced.devices.enums.UiRequestStatus;

public class DeviceUiRequest {
    private final UiRequestStatus mStatus;
    private final String mMessage;

    public DeviceUiRequest(final UiRequestStatus status) {
        mStatus = status;
        mMessage = "No message";
    }

    public DeviceUiRequest(final UiRequestStatus status, final String message) {
        mStatus = status;
        mMessage = message;
    }

    public UiRequestStatus getStatus() {
        return mStatus;
    }

    public String getMessage() {
        return mMessage;
    }
}
