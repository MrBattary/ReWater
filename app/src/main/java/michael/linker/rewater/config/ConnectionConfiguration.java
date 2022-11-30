package michael.linker.rewater.config;

import com.polidea.rxandroidble3.RxBleClient;

import michael.linker.rewater.core.App;

public class ConnectionConfiguration {
    private RxBleClient mRxBleClient;

    public RxBleClient getRxBleClient() {
        if (mRxBleClient == null) {
            mRxBleClient = RxBleClient.create(App.getInstance().getApplicationContext());
        }
        return mRxBleClient;
    }
}
