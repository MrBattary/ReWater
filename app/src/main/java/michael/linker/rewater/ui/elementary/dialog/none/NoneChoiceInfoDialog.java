package michael.linker.rewater.ui.elementary.dialog.none;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import michael.linker.rewater.R;
import michael.linker.rewater.ui.elementary.dialog.IDialog;

public class NoneChoiceInfoDialog implements IDialog {
    private final AlertDialog mAlertDialog;

    public NoneChoiceInfoDialog(
            final Context fragmentContext,
            final NoneChoiceDialogModel dialogModel) {
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(fragmentContext,
                R.style.MaterialInfoDialogStyle);
        dialogBuilder.setIcon(dialogModel.getIconDrawable());
        dialogBuilder.setTitle(dialogModel.getTitle());
        dialogBuilder.setMessage(dialogModel.getMessage());
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
