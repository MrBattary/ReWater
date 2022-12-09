package michael.linker.rewater.ui.elementary.dialog.one;

import android.content.Context;
import android.content.DialogInterface;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import michael.linker.rewater.R;
import michael.linker.rewater.ui.elementary.dialog.IDialog;

public class SingleChoiceInfoDialog implements IDialog {
    private final MaterialAlertDialogBuilder mDialogBuilder;

    public SingleChoiceInfoDialog(
            final Context fragmentContext,
            final SingleChoiceDialogModel dialogModel,
            final DialogInterface.OnClickListener acceptListener) {
        mDialogBuilder = new MaterialAlertDialogBuilder(fragmentContext,
                R.style.MaterialInfoDialogStyle);
        mDialogBuilder.setIcon(dialogModel.getIconDrawable());
        mDialogBuilder.setTitle(dialogModel.getTitle());
        mDialogBuilder.setMessage(dialogModel.getMessage());
        mDialogBuilder.setPositiveButton(dialogModel.getAcceptButtonText(), acceptListener);
        mDialogBuilder.setCancelable(false);
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
