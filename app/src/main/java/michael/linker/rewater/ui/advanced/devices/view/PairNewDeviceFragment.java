package michael.linker.rewater.ui.advanced.devices.view;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.google.android.material.button.MaterialButton;

import me.aflak.bluetooth.interfaces.DeviceCallback;
import michael.linker.rewater.R;
import michael.linker.rewater.config.BuildConfiguration;
import michael.linker.rewater.data.model.status.Status;
import michael.linker.rewater.data.res.ColorsProvider;
import michael.linker.rewater.data.res.DrawablesProvider;
import michael.linker.rewater.data.res.IntegersProvider;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.ui.advanced.devices.enums.AddPairNewDeviceLook;
import michael.linker.rewater.ui.advanced.devices.enums.UiRequestStatus;
import michael.linker.rewater.ui.advanced.devices.model.BluetoothDeviceUiModel;
import michael.linker.rewater.ui.advanced.devices.model.DeviceUiRequest;
import michael.linker.rewater.ui.advanced.devices.model.bluetooth.BluetoothDeviceKeyApiModel;
import michael.linker.rewater.ui.advanced.devices.model.bluetooth.BluetoothDeviceKeyResponseApiModel;
import michael.linker.rewater.ui.advanced.devices.model.bluetooth.BluetoothDeviceNetworkApiModel;
import michael.linker.rewater.ui.advanced.devices.model.bluetooth.BluetoothDeviceNetworkResponseApiModel;
import michael.linker.rewater.ui.advanced.devices.viewmodel.DevicesViewModel;
import michael.linker.rewater.ui.advanced.devices.viewmodel.PairNewDeviceBluetoothViewModel;
import michael.linker.rewater.ui.advanced.devices.viewmodel.PairNewDeviceViewModel;
import michael.linker.rewater.ui.advanced.devices.viewmodel.PairNewDeviceViewModelNotFoundException;
import michael.linker.rewater.ui.elementary.dialog.IDialog;
import michael.linker.rewater.ui.elementary.dialog.none.NoneChoiceDialogModel;
import michael.linker.rewater.ui.elementary.dialog.none.NoneChoiceInfoDialog;
import michael.linker.rewater.ui.elementary.input.text.IPasswordTextInputView;
import michael.linker.rewater.ui.elementary.input.text.ITextInputView;
import michael.linker.rewater.ui.elementary.input.text.PasswordTextInputView;
import michael.linker.rewater.ui.elementary.input.text.TextInputView;
import michael.linker.rewater.ui.elementary.text.status.IStatusStyledTextView;
import michael.linker.rewater.ui.elementary.text.status.StatusStyledColoredTextView;
import michael.linker.rewater.ui.elementary.toast.ToastProvider;

public class PairNewDeviceFragment extends Fragment {
    private ViewGroup mBluetoothView, mAccessView, mNetworkView;
    private TextView mConnectedDeviceData;
    private ITextInputView mWifiSsidInput;
    private IPasswordTextInputView mAccessKeyInput, mWifiPasswordInput;
    private IStatusStyledTextView mStatusStyledMessage;
    private IDialog mDeviceConnectionDialog;
    private MaterialButton mPairButton, mNextButton, mBackButton, mCancelButton;

    private PairNewDeviceViewModel mViewModel;
    private DevicesViewModel mParentViewModel;
    private PairNewDeviceBluetoothViewModel mBluetoothViewModel;

    private static final BuildConfiguration.Bluetooth BLUETOOTH_MODE =
            BuildConfiguration.getBluetoothMode();
    private static final BuildConfiguration.Bluetooth MODE_STUB =
            BuildConfiguration.Bluetooth.STUB;
    private static final BuildConfiguration.Bluetooth MODE_PROD =
            BuildConfiguration.Bluetooth.PROD;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        NavController navController = NavHostFragment.findNavController(this);
        ViewModelStoreOwner viewModelStoreOwner = navController.getViewModelStoreOwner(
                R.id.root_navigation_devices);

        mViewModel = new ViewModelProvider(this).get(PairNewDeviceViewModel.class);
        mParentViewModel = new ViewModelProvider(viewModelStoreOwner).get(DevicesViewModel.class);
        mBluetoothViewModel = new ViewModelProvider(viewModelStoreOwner).get(
                PairNewDeviceBluetoothViewModel.class);
        mBluetoothViewModel.setDeviceCallback(mDeviceCallback);

        return inflater.inflate(R.layout.fragment_devices_add_pair_new, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.initFields(view);
        this.initDialogs();
        this.initButtonsLogic(view);
        this.initObservers(view);
    }

    private void initFields(final View view) {
        mConnectedDeviceData = view.findViewById(R.id.add_device_pair_new_bluetooth_device_data);
        mBluetoothView = view.findViewById(R.id.add_device_pair_new_bluetooth);
        mAccessView = view.findViewById(R.id.add_device_pair_new_access);
        mNetworkView = view.findViewById(R.id.add_device_pair_new_network);

        mAccessKeyInput = new PasswordTextInputView(
                view.findViewById(R.id.add_device_pair_new_access_key),
                view.findViewById(R.id.add_device_pair_new_access_key_input));
        mStatusStyledMessage = new StatusStyledColoredTextView(
                view.findViewById(R.id.add_device_pair_new_message));
        mWifiSsidInput = new TextInputView(
                view.findViewById(R.id.add_device_pair_new_network_input_wifi_ssid),
                view.findViewById(R.id.add_device_pair_new_network_input_wifi_ssid_input)
        );
        mWifiPasswordInput = new PasswordTextInputView(
                view.findViewById(R.id.add_device_pair_new_network_input_wifi_password),
                view.findViewById(R.id.add_device_pair_new_network_input_wifi_password_input)
        );

        mPairButton = view.findViewById(R.id.add_device_pair_new_bluetooth_pair_button);
        mNextButton = view.findViewById(R.id.add_device_pair_new_control_next_button);
        mBackButton = view.findViewById(R.id.add_device_pair_new_control_back_button);
        mCancelButton = view.findViewById(R.id.add_device_pair_new_control_cancel_button);

    }

    private void initDialogs() {
        mDeviceConnectionDialog = new NoneChoiceInfoDialog(requireContext(),
                new NoneChoiceDialogModel(
                        DrawablesProvider.getDrawable(R.drawable.ic_info),
                        StringsProvider.getString(R.string.title_loading),
                        StringsProvider.getString(
                                R.string.dialog_bluetooth_connecting_in_progress)));
    }

    private void initButtonsLogic(final View view) {
        NavController navController = Navigation.findNavController(view);
        mPairButton.setOnClickListener(l -> navController.navigate(
                R.id.navigation_action_devices_add_pair_new_to_devices_add_pair_new_ble_device));
        mBackButton.setOnClickListener(l -> mViewModel.previousLook());
        mCancelButton.setOnClickListener(
                l -> {
                    navController.navigateUp();
                    navController.navigateUp();
                });
    }

    private void initObservers(final View view) {
        mBluetoothViewModel.getBluetoothDeviceData().observe(getViewLifecycleOwner(), model -> {
            if (model != null) {
                mConnectedDeviceData.setText(
                        String.format(StringsProvider.getString(R.string.bluetooth_device_info),
                                (model.getName() != null ? model.getName() : ""), model.getMac()));
                mConnectedDeviceData.setVisibility(View.VISIBLE);
            } else {
                mConnectedDeviceData.setVisibility(View.GONE);
            }
        });

        mViewModel.getDeviceAfterPairingUiModel().observe(getViewLifecycleOwner(),
                deviceModel -> mParentViewModel.setDeviceDuringPairing(deviceModel));

        mViewModel.getCurrentLook().observe(getViewLifecycleOwner(), currentLook -> {
            this.hideMessage();
            this.setVisibilityOfViewGroups(currentLook);
            this.initBackButtonLogic(view, currentLook);
            this.initNextButtonLogic(view, currentLook);

            AutoTransition autoTransition = new AutoTransition();
            autoTransition.setDuration(
                    IntegersProvider.getInteger(R.integer.transition_animation_time));
            TransitionManager.beginDelayedTransition(view.findViewById(R.id.devices_add_pair_new),
                    autoTransition);

            if (currentLook == AddPairNewDeviceLook.FINISH) {
                mStatusStyledMessage.setVisibility(View.VISIBLE);
                mStatusStyledMessage.setText(
                        StringsProvider.getString(R.string.pair_device_success), Status.OK);
            }
            mViewModel.getBluetoothConnected().observe(getViewLifecycleOwner(), request -> {
                this.enableButton(mPairButton);
                if (currentLook == AddPairNewDeviceLook.BLUETOOTH) {
                    this.setSuccessAndFailureMessage(view, request,
                            R.string.pair_device_connect_success,
                            R.string.bluetooth_failure_connect);
                    if (request.getStatus() == UiRequestStatus.OK) {
                        this.allowProceedToTheNextLook();
                    }
                }
            });
            mViewModel.getAccessKeyAccepted().observe(getViewLifecycleOwner(), request -> {
                if (currentLook == AddPairNewDeviceLook.ACCESS) {
                    this.setSuccessAndFailureMessage(view, request,
                            R.string.pair_device_access_success,
                            R.string.pair_device_access_failure);
                    if (request.getStatus() == UiRequestStatus.OK) {
                        this.allowProceedToTheNextLook();
                    }
                }
            });
            mViewModel.getNetworkUpdated().observe(getViewLifecycleOwner(), request -> {
                if (currentLook == AddPairNewDeviceLook.NETWORK) {
                    this.setSuccessAndFailureMessage(view, request,
                            R.string.pair_device_network_success,
                            R.string.pair_device_network_failure);
                    if (request.getStatus() == UiRequestStatus.OK) {
                        this.allowProceedToTheNextLook();
                    }
                }
            });
        });
    }

    private void setVisibilityOfViewGroups(final AddPairNewDeviceLook look) {
        if (look == AddPairNewDeviceLook.BLUETOOTH) {
            mBluetoothView.setVisibility(View.VISIBLE);
            mAccessView.setVisibility(View.GONE);
            mNetworkView.setVisibility(View.GONE);
        }
        if (look == AddPairNewDeviceLook.ACCESS) {
            mBluetoothView.setVisibility(View.GONE);
            mAccessView.setVisibility(View.VISIBLE);
            mNetworkView.setVisibility(View.GONE);
        }
        if (look == AddPairNewDeviceLook.NETWORK) {
            mBluetoothView.setVisibility(View.GONE);
            mAccessView.setVisibility(View.GONE);
            mNetworkView.setVisibility(View.VISIBLE);
        }
        if (look == AddPairNewDeviceLook.FINISH) {
            mBluetoothView.setVisibility(View.GONE);
            mAccessView.setVisibility(View.GONE);
            mNetworkView.setVisibility(View.GONE);
        }
    }

    private void initBackButtonLogic(final View view, final AddPairNewDeviceLook look) {
        mBackButton.setOnClickListener(l -> {
            if (look == AddPairNewDeviceLook.BLUETOOTH) {
                Navigation.findNavController(view).navigateUp();
            } else {
                if (BLUETOOTH_MODE == MODE_STUB) {
                    // STUB DEVICE DISCONNECT
                    if (look == AddPairNewDeviceLook.NETWORK
                            || look == AddPairNewDeviceLook.ACCESS) {
                        mBluetoothViewModel.setConnectableBluetoothDevice(null);
                        mBluetoothViewModel.setBluetoothDeviceData(null);
                    }
                }
                if (BLUETOOTH_MODE == MODE_PROD) {
                    if (look == AddPairNewDeviceLook.NETWORK
                            || look == AddPairNewDeviceLook.ACCESS) {
                        mBluetoothViewModel.disconnectFromDevice();
                    }
                }
                mViewModel.previousLook();
            }
        });
    }

    private void initNextButtonLogic(final View view, final AddPairNewDeviceLook look) {
        this.setNextButtonActionStyle();
        if (look == AddPairNewDeviceLook.BLUETOOTH) {
            this.disableButton(mNextButton);
        }
        if (look == AddPairNewDeviceLook.ACCESS) {
            mNextButton.setOnClickListener(
                    l -> {
                        if (BLUETOOTH_MODE == MODE_STUB) {
                            // STUB DEVICE SEND KEY START
                            mViewModel.sendProvidedDeviceHardwareId("40302010");
                        }
                        if (BLUETOOTH_MODE == MODE_PROD) {
                            try {
                                mBluetoothViewModel.sendKey(
                                        new BluetoothDeviceKeyApiModel(
                                                mAccessKeyInput.getPasswordHash()));
                            } catch (PairNewDeviceViewModelNotFoundException e) {
                                ToastProvider.showShort(requireContext(),
                                        StringsProvider.getString(
                                                R.string.bluetooth_failure_transmit));
                            }
                        }
                    });
        }
        if (look == AddPairNewDeviceLook.NETWORK) {
            mNextButton.setOnClickListener(
                    l -> {
                        if (BLUETOOTH_MODE == MODE_STUB) {
                            // STUB DEVICE SEND NETWORK SETTINGS START
                            if (mWifiSsidInput.getText().length() > 0 &&
                                    mWifiPasswordInput.getPasswordAsPlainText().length() > 8) {
                                mViewModel.setNetworkDataUpdated();
                            } else {
                                mViewModel.setNetworkDataNotUpdated();
                            }
                        }
                        if (BLUETOOTH_MODE == MODE_PROD) {
                            try {
                                mBluetoothViewModel.sendNetworkSettings(
                                        new BluetoothDeviceNetworkApiModel(
                                                mWifiSsidInput.getText(),
                                                mWifiPasswordInput.getPasswordAsPlainText()
                                        ));
                            } catch (PairNewDeviceViewModelNotFoundException e) {
                                ToastProvider.showShort(requireContext(),
                                        StringsProvider.getString(
                                                R.string.bluetooth_failure_transmit));
                            }
                        }
                    });
        }
        if (look == AddPairNewDeviceLook.FINISH) {
            mNextButton.setOnClickListener(l -> Navigation.findNavController(view).navigate(
                    R.id.navigation_action_devices_add_pair_new_to_devices_add));
        }
    }

    private void hideMessage() {
        mStatusStyledMessage.setVisibility(View.GONE);
    }

    private void setSuccessAndFailureMessage(final View view, final DeviceUiRequest request,
            final int successId, final int failureId) {
        AutoTransition autoTransition = new AutoTransition();
        autoTransition.addTarget(view.findViewById(R.id.add_device_pair_new_controls));
        autoTransition.addTarget(view.findViewById(R.id.add_device_pair_new_message));
        autoTransition.setDuration(
                IntegersProvider.getInteger(R.integer.transition_animation_time));
        TransitionManager.beginDelayedTransition(view.findViewById(R.id.devices_add_pair_new),
                autoTransition);

        if (request.getStatus() != UiRequestStatus.UNDEFINED) {
            mStatusStyledMessage.setVisibility(View.VISIBLE);
        }
        if (request.getStatus() == UiRequestStatus.OK) {
            mStatusStyledMessage.setText(StringsProvider.getString(successId), Status.OK);
        }
        if (request.getStatus() == UiRequestStatus.ERROR) {
            mStatusStyledMessage.setText(StringsProvider.getString(failureId), Status.DEFECT);
        }
    }

    private void disableButton(final MaterialButton button) {
        button.setEnabled(false);
        button.setTextColor(ColorsProvider.getColor(R.color.text_disabled));
        button.setBackgroundTintList(
                ColorStateList.valueOf(ColorsProvider.getColor(R.color.background_disabled)));
        button.setStrokeColor(
                ColorStateList.valueOf(ColorsProvider.getColor(R.color.background_disabled)));
    }

    private void enableButton(final MaterialButton button) {
        button.setEnabled(true);
        button.setTextColor(ColorsProvider.getColor(R.color.text_secondary));
        button.setBackgroundTintList(
                ColorStateList.valueOf(ColorsProvider.getColor(R.color.background_secondary)));
        button.setStrokeColor(
                ColorStateList.valueOf(ColorsProvider.getColor(R.color.positive_color)));
    }

    private void setNextButtonActionStyle() {
        mNextButton.setTextColor(ColorsProvider.getColor(R.color.text_secondary));
        mNextButton.setBackgroundTintList(
                ColorStateList.valueOf(ColorsProvider.getColor(R.color.background_secondary)));
        mNextButton.setStrokeColor(
                ColorStateList.valueOf(ColorsProvider.getColor(R.color.background_secondary)));
    }

    private void allowProceedToTheNextLook() {
        mNextButton.setOnClickListener(l -> mViewModel.nextLook());
        this.enableButton(mNextButton);
    }

    // TODO (ML): STUB DEVICE CONNECT START
    @SuppressLint("MissingPermission")
    // TODO (ML): STUB DEVICE CONNECT END
    @Override
    public void onStart() {
        super.onStart();
        mBluetoothViewModel.onStart();
        final BluetoothDevice bluetoothDevice = mBluetoothViewModel.getConnectableBluetoothDevice();
        if (BLUETOOTH_MODE == MODE_STUB) {
            // STUB DEVICE CONNECT
            if (bluetoothDevice != null) {
                mDeviceConnectionDialog.show();
                mBluetoothViewModel.setBluetoothDeviceData(
                        new BluetoothDeviceUiModel(
                                bluetoothDevice.getName(),
                                bluetoothDevice.getAddress()));
                mViewModel.setConnectedToDevice();
                mDeviceConnectionDialog.dismiss();
            }

        }
        if (BLUETOOTH_MODE == MODE_PROD) {
            if (bluetoothDevice != null && !mBluetoothViewModel.isConnectedToDevice()) {
                mDeviceConnectionDialog.show();
                mBluetoothViewModel.connectToDevice(bluetoothDevice);
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mBluetoothViewModel.setBluetoothDeviceData(null);
        mBluetoothViewModel.setConnectableBluetoothDevice(null);
        mBluetoothViewModel.removeCallbacks();
        mBluetoothViewModel.onStop();
    }

    private final DeviceCallback mDeviceCallback = new DeviceCallback() {
        @SuppressLint("MissingPermission")
        @Override
        public void onDeviceConnected(BluetoothDevice device) {
            mBluetoothViewModel.setBluetoothDeviceData(
                    new BluetoothDeviceUiModel(
                            device.getName(),
                            device.getAddress()));
            mDeviceConnectionDialog.dismiss();
            mViewModel.setConnectedToDevice();
        }

        @Override
        public void onDeviceDisconnected(BluetoothDevice device, String message) {
            mBluetoothViewModel.setConnectableBluetoothDevice(null);
            mBluetoothViewModel.setBluetoothDeviceData(null);
        }

        @Override
        public void onMessage(byte[] message) {

            if (mViewModel.getCurrentLook().getValue() == AddPairNewDeviceLook.ACCESS) {
                try {
                    BluetoothDeviceKeyResponseApiModel response =
                            mBluetoothViewModel.getKeyResponse(message);

                    if (response.isSuccess()) {
                        mViewModel.sendProvidedDeviceHardwareId(response.getHardwareId());
                    }
                } catch (PairNewDeviceViewModelNotFoundException ignored) {
                }
            }
            if (mViewModel.getCurrentLook().getValue() == AddPairNewDeviceLook.NETWORK) {
                try {
                    BluetoothDeviceNetworkResponseApiModel response =
                            mBluetoothViewModel.getNetworkSettingsResponse(message);
                    if (response.isSuccess()) {
                        mViewModel.setNetworkDataUpdated();
                    } else {
                        mViewModel.setNetworkDataNotUpdated();
                    }
                } catch (PairNewDeviceViewModelNotFoundException ignored) {
                }
            }
        }

        @Override
        public void onError(int errorCode) {
            ToastProvider.showShort(requireContext(), String.valueOf(errorCode));
        }

        @Override
        public void onConnectError(BluetoothDevice device, String message) {
            ToastProvider.showShort(requireContext(),
                    StringsProvider.getString(R.string.bluetooth_failure_connect));
            mDeviceConnectionDialog.dismiss();
            mViewModel.setNotConnectedToDevice();
        }
    };
}