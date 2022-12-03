package michael.linker.rewater.ui.elementary.dialog.two;

import android.content.Context;
import android.content.DialogInterface;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import michael.linker.rewater.R;
import michael.linker.rewater.ui.elementary.dialog.IDialog;

public class TwoChoicesConfirmDialog implements IDialog {
    private final MaterialAlertDialogBuilder mDialogBuilder;

    public TwoChoicesConfirmDialog(
            final Context fragmentContext,
            final TwoChoicesDialogModel dialogModel,
            final DialogInterface.OnClickListener acceptListener,
            final DialogInterface.OnClickListener rejectListener) {
        mDialogBuilder = new MaterialAlertDialogBuilder(fragmentContext,
                R.style.MaterialConfirmDialogStyle);
        mDialogBuilder.setIcon(dialogModel.getIconDrawable());
        mDialogBuilder.setTitle(dialogModel.getTitle());
        mDialogBuilder.setMessage(dialogModel.getMessage());
        mDialogBuilder.setNegativeButton(dialogModel.getAcceptButtonText(), acceptListener);
        mDialogBuilder.setPositiveButton(dialogModel.getRejectButtonText(), rejectListener);
        mDialogBuilder.create();
    }

    @Override
    public void show() {
        mDialogBuilder.show();
    }

    @Override
    public void dismiss() {
    }
}
