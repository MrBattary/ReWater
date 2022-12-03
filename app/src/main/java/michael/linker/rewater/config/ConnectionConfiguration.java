package michael.linker.rewater.config;

import android.app.Activity;

import com.polidea.rxandroidble3.RxBleClient;

import me.aflak.bluetooth.Bluetooth;
import michael.linker.rewater.core.App;

public class ConnectionConfiguration {
    private static RxBleClient sRxBleClient;
    private static Bluetooth sBluetooth;

    public static RxBleClient getRxBleClient() {
        if (sRxBleClient == null) {
            sRxBleClient = RxBleClient.create(App.getInstance().getApplicationContext());
        }
        return sRxBleClient;
    }

    public static void buildBluetooth(final Activity activity) {
        if (sBluetooth == null) {
            sBluetooth = new Bluetooth(App.getInstance().getApplicationContext());
            sBluetooth.setCallbackOnUI(activity);
        }
    }

    public static Bluetooth getBluetooth() throws ConfigurationNotFoundException {
        if (sBluetooth == null) {
            throw new ConfigurationNotFoundException();
        }
        return sBluetooth;
    }
}
