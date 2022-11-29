package michael.linker.rewater.activity;

import android.app.Activity;
import android.content.Intent;

import michael.linker.rewater.activity.intent.SignOutIntent;
import michael.linker.rewater.activity.intent.SignOutIntentModel;

public class ActivityGate {
    public static void moveToMainActivity(final Activity activity) {
        final Intent intent = new Intent(activity, MainActivity.class);
        ActivityGate.moveToActivity(activity, intent);
    }

    public static void moveToSignActivity(final Activity activity) {
        final Intent intent = new Intent(activity, SignActivity.class);
        ActivityGate.moveToActivity(activity, intent);
    }

    public static void moveToSignActivityOnSignOut(final Activity activity) {
        final Intent intent = SignOutIntent.INSTANCE.pack(
                new SignOutIntentModel(SignOutIntent.EXPECTED_SIGN_OUT),
                new Intent(activity, SignActivity.class));
        ActivityGate.moveToActivity(activity, intent);
    }

    /**
     * After executing the function, the activity will be closed, but the processes associated
     * with the App will remain in the system, such as connecting to the database and so on.
     *
     * @param activity active activity
     */
    public static void finishApplication(final Activity activity) {
        activity.finishAndRemoveTask();
    }

    private static void moveToActivity(final Activity activity, final Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        activity.startActivity(intent);
        activity.finish();
    }
}
