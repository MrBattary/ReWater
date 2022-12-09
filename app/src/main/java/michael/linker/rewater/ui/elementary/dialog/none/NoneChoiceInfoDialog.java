package michael.linker.rewater.ui.elementary.dialog.none;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import michael.linker.rewater.R;
import michael.linker.rewater.ui.elementary.dialog.IDialog;

public class NoneChoiceInfoDialog implements IDialog {
    private final MaterialAlertDialogBuilder mDialogBuilder;
    private final AlertDialog mAlertDialog;

    public NoneChoiceInfoDialog(
            final Context fragmentContext,
            final NoneChoiceDialogModel dialogModel) {
        mDialogBuilder = new MaterialAlertDialogBuilder(fragmentContext,
                R.style.MaterialInfoDialogStyle);
        mDialogBuilder.setIcon(dialogModel.getIconDrawable());
        mDialogBuilder.setTitle(dialogModel.getTitle());
        mDialogBuilder.setMessage(dialogModel.getMessage());
        mDialogBuilder.setCancelable(false);
        mAlertDialog = mDialogBuilder.create();
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
