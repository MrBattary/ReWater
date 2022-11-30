package michael.linker.rewater.core.connection.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;

import michael.linker.rewater.R;
import michael.linker.rewater.core.App;
import michael.linker.rewater.core.permission.PermissionManager;
import michael.linker.rewater.data.res.StringsProvider;

public class Bluetooth {
    private static final BluetoothManager BLUETOOTH_MANAGER;
    private static final BluetoothAdapter BLUETOOTH_ADAPTER;

    static {
        BLUETOOTH_MANAGER = App.getInstance().getSystemService(BluetoothManager.class);
        BLUETOOTH_ADAPTER = BLUETOOTH_MANAGER.getAdapter();
    }

    public static boolean isDeviceHaveBluetooth() {
        return BLUETOOTH_ADAPTER != null;
    }

    public static boolean isBluetoothEnabled() {
        if (isDeviceHaveBluetooth()) {
            return BLUETOOTH_ADAPTER.isEnabled();
        }
        return false;
    }

    public static boolean isRequiredPermissionsGranted() {
        return PermissionManager.isPermissionGranted(
                PermissionManager.Permission.BLUETOOTH_CONNECT);
    }

    public static void enableBluetooth() throws BluetoothFailedException, BluetoothNotFoundException {
        if (!isDeviceHaveBluetooth()) {
            throw new BluetoothNotFoundException(
                    StringsProvider.getString(R.string.bluetooth_failure_device_not_support));
        }
        if (!isRequiredPermissionsGranted()) {
            throw new BluetoothNotFoundException(
                    StringsProvider.getString(R.string.bluetooth_failure_permission_not_found));
        }
        try {
            BLUETOOTH_ADAPTER.enable();
        } catch (SecurityException e) {
            throw new BluetoothFailedException(
                    StringsProvider.getString(R.string.bluetooth_failure_turn_on));
        }
    }
}
