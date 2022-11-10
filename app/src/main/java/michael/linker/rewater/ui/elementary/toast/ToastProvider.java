package michael.linker.rewater.ui.elementary.toast;

import android.content.Context;
import android.widget.Toast;

public class ToastProvider {
    private final static int SHORT_DURATION = Toast.LENGTH_SHORT;
    private final static int LONG_DURATION = Toast.LENGTH_LONG;

    public static void showShort(final Context ctx, final String msg) {
        Toast.makeText(ctx, msg, SHORT_DURATION).show();
    }

    public static void showLong(final Context ctx, final String msg) {
        Toast.makeText(ctx, msg, LONG_DURATION).show();
    }
}
