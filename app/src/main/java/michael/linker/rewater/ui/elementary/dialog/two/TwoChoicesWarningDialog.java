package michael.linker.rewater.ui.elementary.dialog.two;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import michael.linker.rewater.R;
import michael.linker.rewater.ui.elementary.dialog.IDialog;

public class TwoChoicesWarningDialog implements IDialog {
    private final AlertDialog mAlertDialog;

    public TwoChoicesWarningDialog(
            final Context fragmentContext,
            final TwoChoicesDialogModel dialogModel,
            final DialogInterface.OnClickListener acceptListener,
            final DialogInterface.OnClickListener rejectListener) {
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(fragmentContext,
                R.style.MaterialWarningDialogStyle);
        dialogBuilder.setIcon(dialogModel.getIconDrawable());
        dialogBuilder.setTitle(dialogModel.getTitle());
        dialogBuilder.setMessage(dialogModel.getMessage());
        dialogBuilder.setNegativeButton(dialogModel.getAcceptButtonText(), acceptListener);
        dialogBuilder.setPositiveButton(dialogModel.getRejectButtonText(), rejectListener);
        dialogBuilder.setCancelable(false);
        mAlertDialog = dialogBuilder.create();
    }

    @Override
    public void show() {
        mAlertDialog.show();
    }

    @Override
    public void dismiss() {
        mAlertDialog.dismiss();
    }
}
