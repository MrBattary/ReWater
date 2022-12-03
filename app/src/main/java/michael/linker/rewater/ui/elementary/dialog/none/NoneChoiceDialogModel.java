package michael.linker.rewater.ui.elementary.dialog.none;

import android.graphics.drawable.Drawable;

public class NoneChoiceDialogModel {
    final Drawable mIconDrawable;
    final String mTitle, mMessage;

    public NoneChoiceDialogModel(
            final Drawable iconDrawable,
            final String title,
            final String message) {
        mIconDrawable = iconDrawable;
        mTitle = title;
        mMessage = message;
    }

    public Drawable getIconDrawable() {
        return mIconDrawable;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getMessage() {
        return mMessage;
    }
}
