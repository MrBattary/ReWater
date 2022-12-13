package michael.linker.rewater.ui.advanced.settings.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;

import michael.linker.rewater.R;
import michael.linker.rewater.activity.ActivityGate;
import michael.linker.rewater.config.BuildConfiguration;
import michael.linker.rewater.data.res.DrawablesProvider;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.ui.elementary.dialog.IDialog;
import michael.linker.rewater.ui.elementary.dialog.two.TwoChoicesDialogModel;
import michael.linker.rewater.ui.elementary.dialog.two.TwoChoicesWarningDialog;

public class SettingsFragment extends Fragment {
    private TextView mAppInfoTextView;
    private MaterialButton mLicenseButton, mExitButton, mShutdownButton;
    private IDialog mExitDialog, mShutdownDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.initFields(view);
        this.initFieldsData();
        this.initDialogs();
        this.initButtons(view);
    }

    private void initFields(final View view) {
        mAppInfoTextView = view.findViewById(R.id.settings_app_info);
        mLicenseButton = view.findViewById(R.id.settings_license_button);
        mExitButton = view.findViewById(R.id.settings_exit_button);
        mShutdownButton = view.findViewById(R.id.settings_shutdown_button);
    }

    private void initFieldsData() {
        mAppInfoTextView.setText(
                String.format("%s%s%s",
                        StringsProvider.getString(R.string.app_name),
                        " ",
                        BuildConfiguration.getVersionName()));
    }

    private void initButtons(final View view) {
        mLicenseButton.setOnClickListener(l -> Navigation.findNavController(view).navigate(
                R.id.navigation_action_settings_to_settings_license));
        mExitButton.setOnClickListener(l -> mExitDialog.show());
        mShutdownButton.setOnClickListener(l -> mShutdownDialog.show());
    }

    private void initDialogs() {
        mExitDialog = new TwoChoicesWarningDialog(requireContext(),
                new TwoChoicesDialogModel(
                        DrawablesProvider.getDrawable(R.drawable.ic_info),
                        StringsProvider.getString(R.string.title_exit),
                        StringsProvider.getString(R.string.dialog_exit),
                        StringsProvider.getString(R.string.button_exit),
                        StringsProvider.getString(R.string.button_cancel)
                ),
                (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    ActivityGate.finishApplication(requireActivity());
                },
                (dialogInterface, i) -> dialogInterface.dismiss()
        );
        mShutdownDialog = new TwoChoicesWarningDialog(requireContext(),
                new TwoChoicesDialogModel(
                        DrawablesProvider.getDrawable(R.drawable.ic_info),
                        StringsProvider.getString(R.string.title_shutdown),
                        StringsProvider.getString(R.string.dialog_shutdown),
                        StringsProvider.getString(R.string.button_shutdown),
                        StringsProvider.getString(R.string.button_cancel)
                ),
                (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    ActivityGate.shutdownApplication(requireActivity());
                },
                (dialogInterface, i) -> dialogInterface.dismiss()
        );
    }
}