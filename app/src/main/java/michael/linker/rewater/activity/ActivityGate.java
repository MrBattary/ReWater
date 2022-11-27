package michael.linker.rewater.activity;

import android.app.Activity;
import android.content.Intent;

import michael.linker.rewater.config.DatabaseConfiguration;

public class ActivityGate {
    public static void moveToMainActivity(final Activity activity) {
        final Intent intent = new Intent(activity, MainActivity.class);
        ActivityGate.moveToActivity(activity, intent);
    }

    public static void moveToSignActivity(final Activity activity) {
        final Intent intent = new Intent(activity, SignActivity.class);
        ActivityGate.moveToActivity(activity, intent);
    }

    public static void finishApplication(final Activity activity) {
        DatabaseConfiguration.getDatabase().close();
        activity.finishAndRemoveTask();
    }

    private static void moveToActivity(final Activity activity, final Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        activity.startActivity(intent);
        activity.finish();
    }
}
