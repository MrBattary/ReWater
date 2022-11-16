package michael.linker.rewater.ui.elementary.dialog.two;

import android.graphics.drawable.Drawable;

public class TwoChoicesWarningDialogModel {
    final Drawable mIconDrawable;
    final String mTitle, mMessage;
    final String mAcceptButtonText, mRejectButtonText;

    public TwoChoicesWarningDialogModel(
            final Drawable iconDrawable,
            final String title,
            final String message,
            final String acceptButtonText,
            final String rejectButtonText) {
        mIconDrawable = iconDrawable;
        mTitle = title;
        mMessage = message;
        mAcceptButtonText = acceptButtonText;
        mRejectButtonText = rejectButtonText;
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

    public String getRejectButtonText() {
        return mRejectButtonText;
    }
}
