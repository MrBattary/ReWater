package michael.linker.rewater.ui.advanced.devices.view;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;

import michael.linker.rewater.R;
import michael.linker.rewater.core.connection.bluetooth.Bluetooth;
import michael.linker.rewater.core.connection.bluetooth.BluetoothFailedException;
import michael.linker.rewater.core.connection.bluetooth.BluetoothNotFoundException;
import michael.linker.rewater.data.res.ColorsProvider;
import michael.linker.rewater.data.res.DrawablesProvider;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.ui.elementary.dialog.two.TwoChoicesConfirmDialog;
import michael.linker.rewater.ui.elementary.dialog.two.TwoChoicesDialogModel;
import michael.linker.rewater.ui.elementary.toast.ToastProvider;

public class AddDeviceOptionsFragment extends Fragment {
    private MaterialButton mPairNewButton, mAlreadyPairedButton, mCancelButton;
    private TwoChoicesConfirmDialog mTurnOnBluetoothDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_devices_add_options, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.initFields(view);
        this.initButtons(view);
        this.initDialogs(view);

        // Disable the mAlreadyPaired button
        mAlreadyPairedButton.setEnabled(false);
        mAlreadyPairedButton.setTextColor(ColorsProvider.getColor(R.color.text_disabled));
        mAlreadyPairedButton.setBackgroundTintList(
                ColorStateList.valueOf(ColorsProvider.getColor(R.color.background_disabled)));
    }

    private void initFields(final View view) {
        mPairNewButton = view.findViewById(R.id.add_device_options_pair_new_button);
        mAlreadyPairedButton = view.findViewById(R.id.add_device_options_already_paired_button);
        mCancelButton = view.findViewById(R.id.add_device_options_cancel_button);
    }

    private void initButtons(final View view) {
        NavController navController = Navigation.findNavController(view);
        mPairNewButton.setOnClickListener(l -> {
            if (Bluetooth.isBluetoothEnabled()) {
                navController.navigate(
                        R.id.navigation_action_devices_add_options_to_devices_add_pair_new);
            } else {
                mTurnOnBluetoothDialog.show();
            }
        });
        mCancelButton.setOnClickListener(
                l -> navController.popBackStack(R.id.navigation_devices_add_options, true));
    }

    private void initDialogs(final View view) {
        mTurnOnBluetoothDialog = new TwoChoicesConfirmDialog(requireContext(),
                new TwoChoicesDialogModel(
                        DrawablesProvider.getDrawable(R.drawable.ic_warning),
                        StringsProvider.getString(R.string.title_warning),
                        StringsProvider.getString(R.string.dialog_bluetooth_turn_on_to_pair),
                        StringsProvider.getString(R.string.button_turn_on),
                        StringsProvider.getString(R.string.button_cancel)),
                (dialogInterface, i) -> {
                    try {
                        Bluetooth.enableBluetooth();
                        Navigation.findNavController(view).navigate(
                                R.id.navigation_action_devices_add_options_to_devices_add_pair_new);
                    } catch (BluetoothFailedException | BluetoothNotFoundException e) {
                        ToastProvider.showLong(requireContext(), e.getLocalizedMessage());
                    }
                },
                (dialogInterface, i) -> dialogInterface.dismiss());
    }
}