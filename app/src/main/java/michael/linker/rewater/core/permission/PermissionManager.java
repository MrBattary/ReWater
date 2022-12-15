package michael.linker.rewater.core.permission;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import michael.linker.rewater.core.App;

/**
 * Permission wrapper
 */
public class PermissionManager {
    private static final Map<Permission, Version> REQUIRED_PERMISSION_VERSION_MAP;

    static {
        REQUIRED_PERMISSION_VERSION_MAP = Map.of(
                Permission.INTERNET, Version.ANY,
                Permission.FINE_LOCATION, Version.ANY,
                Permission.COARSE_LOCATION, Version.ANY,
                Permission.BLUETOOTH_CONNECT, Version.S,
                Permission.BLUETOOTH_SCAN, Version.S
        );
    }

    /**
     * Returns a list of permissions that were not given by the user.
     * Checks in the provided context.
     *
     * @param ctx provided context
     * @return list of permissions
     */
    public static List<String> getAwaitedPermissions(final Context ctx) {
        final List<String> awaitedPermissions = new ArrayList<>();
        for (Permission requiredPermission : REQUIRED_PERMISSION_VERSION_MAP.keySet()) {
            Version requiredPermissionVersion =
                    REQUIRED_PERMISSION_VERSION_MAP.get(requiredPermission);
            if (Build.VERSION.SDK_INT >= requiredPermissionVersion.mVersion) {
                if (ctx.checkSelfPermission(requiredPermission.mPermission)
                        != Permission.Status.GRANTED) {
                    awaitedPermissions.add(requiredPermission.mPermission);
                }
            }
        }
        return awaitedPermissions;
    }

    /**
     * Returns a list of permissions that were not given by the user.
     * Checks in the Application Context.
     *
     * @return list of permissions
     */
    public static List<String> getAwaitedPermissions() {
        return PermissionManager.getAwaitedPermissions(App.getInstance().getApplicationContext());
    }

    /**
     * Checks is app have the provided permission.
     *
     * @param permission required permission
     * @param ctx        provided context
     * @return true if have or not required, false otherwise
     */
    public static boolean isPermissionGranted(final Permission permission, final Context ctx) {
        Version requiredPermissionVersion =
                REQUIRED_PERMISSION_VERSION_MAP.get(permission);
        if (Build.VERSION.SDK_INT >= requiredPermissionVersion.mVersion) {
            return ctx.checkSelfPermission(permission.mPermission) == Permission.Status.GRANTED;
        }
        return true;
    }

    /**
     * Checks is app have the provided permission.
     * Checks in the Application Context.
     *
     * @param permission required permission
     * @return true if have or not required, false otherwise
     */
    public static boolean isPermissionGranted(final Permission permission) {
        return isPermissionGranted(permission, App.getInstance().getApplicationContext());
    }

    public static class Permission {
        public static final Permission BLUETOOTH;
        public static final Permission BLUETOOTH_CONNECT;
        public static final Permission BLUETOOTH_SCAN;
        public static final Permission FINE_LOCATION;
        public static final Permission COARSE_LOCATION;
        public static final Permission INTERNET;

        static {
            BLUETOOTH = new Permission(Manifest.permission.BLUETOOTH);
            BLUETOOTH_CONNECT = new Permission(Manifest.permission.BLUETOOTH_CONNECT);
            BLUETOOTH_SCAN = new Permission(Manifest.permission.BLUETOOTH_SCAN);
            FINE_LOCATION = new Permission(Manifest.permission.ACCESS_FINE_LOCATION);
            COARSE_LOCATION = new Permission(Manifest.permission.ACCESS_COARSE_LOCATION);
            INTERNET = new Permission(Manifest.permission.INTERNET);
        }

        private final String mPermission;

        private Permission(String permission) {
            mPermission = permission;
        }

        private static class Status {
            private static final int GRANTED = PackageManager.PERMISSION_GRANTED;
        }
    }

    private static class Version {
        private static final Version ANY;
        private static final Version S;

        static {
            ANY = new Version(Build.VERSION_CODES.BASE);
            S = new Version(Build.VERSION_CODES.S);
        }

        private final Integer mVersion;

        private Version(Integer version) {
            mVersion = version;
        }
    }
}
