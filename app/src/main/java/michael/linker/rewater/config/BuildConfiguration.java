package michael.linker.rewater.config;

import michael.linker.rewater.BuildConfig;

public class BuildConfiguration {
    public static String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    public static Server getServerMode() {
        return BuildConfig.SERVER_MODE;
    }

    public static ServerProtocol getServerProtocol() {
        return BuildConfig.SERVER_PROTOCOL;
    }

    public static String getPingAddress() {
        return BuildConfig.PING_ADDRESS;
    }

    public static String getServerAddress() {
        return BuildConfig.SERVER_ADDRESS;
    }

    public static Boolean isInternetHealthCheckEnabled() {
        return BuildConfig.HEALTHCHECK_INTERNET;
    }

    public static Boolean isServerHealthCheckEnabled() {
        return BuildConfig.HEALTHCHECK_SERVER;
    }

    public static Bluetooth getBluetoothMode() {
        return BuildConfig.BLUETOOTH_MODE;
    }

    public enum ServerProtocol {
        HTTP,
        HTTPS
    }

    public enum Server {
        GLOBAL,
        LOCAL
    }

    public enum Bluetooth {
        PROD,
        STUB
    }
}
