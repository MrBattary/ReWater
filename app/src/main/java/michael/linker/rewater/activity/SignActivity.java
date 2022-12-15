package michael.linker.rewater.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import michael.linker.rewater.R;
import michael.linker.rewater.data.res.DrawablesProvider;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.ui.elementary.dialog.two.TwoChoicesDialogModel;
import michael.linker.rewater.ui.elementary.dialog.two.TwoChoicesWarningDialog;

public class SignActivity extends AppCompatActivity {
    private TwoChoicesWarningDialog mExitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initExitDialog();
        setContentView(R.layout.activity_sign);
    }

    @Override
    public void onBackPressed() {
        if (!getOnBackPressedDispatcher().hasEnabledCallbacks()) {
            mExitDialog.show();
        } else {
            super.onBackPressed();
        }
    }

    private void initExitDialog() {
        mExitDialog = new TwoChoicesWarningDialog(this,
                new TwoChoicesDialogModel(
                        DrawablesProvider.getDrawable(R.drawable.ic_info),
                        StringsProvider.getString(R.string.title_exit),
                        StringsProvider.getString(R.string.dialog_exit),
                        StringsProvider.getString(R.string.button_exit),
                        StringsProvider.getString(R.string.button_cancel)
                ),
                (dialogInterface, i) -> ActivityGate.finishApplication(this),
                (dialogInterface, i) -> dialogInterface.dismiss()
        );
    }
}