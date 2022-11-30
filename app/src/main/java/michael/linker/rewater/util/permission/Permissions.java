package michael.linker.rewater.util.permission;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import michael.linker.rewater.data.res.App;

/**
 * Permission wrapper
 */
public class Permissions {
    private static final Map<String, Integer> REQUIRED_PERMISSION_VERSION_MAP;

    static {
        REQUIRED_PERMISSION_VERSION_MAP = Map.of(
                Permission.INTERNET, Version.ANY,
                Permission.BLUETOOTH_CONNECT, Version.S
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
        for (String requiredPermissionName : REQUIRED_PERMISSION_VERSION_MAP.keySet()) {
            int requiredPermissionVersion =
                    REQUIRED_PERMISSION_VERSION_MAP.get(requiredPermissionName);
            if (Build.VERSION.SDK_INT >= requiredPermissionVersion) {
                if (ctx.checkSelfPermission(requiredPermissionName) != Permission.GRANTED) {
                    awaitedPermissions.add(requiredPermissionName);
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
        return Permissions.getAwaitedPermissions(App.getInstance().getApplicationContext());
    }

    private static class Permission {
        private static final int GRANTED = PackageManager.PERMISSION_GRANTED;

        private static final String BLUETOOTH_CONNECT = Manifest.permission.BLUETOOTH_CONNECT;
        private static final String INTERNET = Manifest.permission.INTERNET;
    }

    private static class Version {
        private static final Integer ANY = Build.VERSION_CODES.BASE;
        private static final Integer S = Build.VERSION_CODES.S;
    }
}
