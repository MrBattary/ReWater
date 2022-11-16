package michael.linker.rewater.ui.elementary.dialog.one;

import android.graphics.drawable.Drawable;

public class SingleChoiceInfoDialogModel {
    final Drawable mIconDrawable;
    final String mTitle, mMessage;
    final String mAcceptButtonText;

    public SingleChoiceInfoDialogModel(
            final Drawable iconDrawable,
            final String title,
            final String message,
            final String acceptButtonText) {
        mIconDrawable = iconDrawable;
        mTitle = title;
        mMessage = message;
        mAcceptButtonText = acceptButtonText;
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

    public String getAcceptButtonText() {
        return mAcceptButtonText;
    }
}
