package michael.linker.rewater.ui.advanced.devices.view;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import michael.linker.rewater.R;
import michael.linker.rewater.data.model.status.Status;
import michael.linker.rewater.data.res.ColorsProvider;
import michael.linker.rewater.data.res.IntegersProvider;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.ui.advanced.devices.enums.AddPairNewDeviceLook;
import michael.linker.rewater.ui.advanced.devices.enums.UiRequestStatus;
import michael.linker.rewater.ui.advanced.devices.model.DeviceUiRequest;
import michael.linker.rewater.ui.advanced.devices.viewmodel.AddPairNewDeviceViewModel;
import michael.linker.rewater.ui.advanced.devices.viewmodel.DevicesViewModel;
import michael.linker.rewater.ui.elementary.input.InputNotAllowedException;
import michael.linker.rewater.ui.elementary.input.text.ITextInputView;
import michael.linker.rewater.ui.elementary.input.text.TextInputView;
import michael.linker.rewater.ui.elementary.text.status.IStatusStyledTextView;
import michael.linker.rewater.ui.elementary.text.status.StatusStyledColoredTextView;
import michael.linker.rewater.ui.elementary.toast.ToastProvider;

public class AddPairNewDeviceFragment extends Fragment {
    private ViewGroup mBluetoothView, mAccessView, mNetworkView;
    private ITextInputView mAccessKeyInput;
    private IStatusStyledTextView mStatusStyledMessage;
    private MaterialButton mPairButton, mNextButton, mBackButton, mCancelButton;

    private AddPairNewDeviceViewModel mViewModel;
    private DevicesViewModel mParentViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        NavController navController = NavHostFragment.findNavController(this);
        ViewModelStoreOwner viewModelStoreOwner = navController.getViewModelStoreOwner(
                R.id.root_navigation_devices);

        mViewModel = new ViewModelProvider(this).get(AddPairNewDeviceViewModel.class);
        mParentViewModel = new ViewModelProvider(viewModelStoreOwner).get(DevicesViewModel.class);

        return inflater.inflate(R.layout.fragment_devices_add_pair_new, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.initFields(view);
        this.initButtonsLogic(view);

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
                            R.string.pair_device_connect_failure);
                    if (request.getStatus() == UiRequestStatus.OK) {
                        this.disableButton(mPairButton);
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

    private void initFields(final View view) {
        mBluetoothView = view.findViewById(R.id.add_device_pair_new_bluetooth);
        mAccessView = view.findViewById(R.id.add_device_pair_new_access);
        mNetworkView = view.findViewById(R.id.add_device_pair_new_network);

        mAccessKeyInput = new TextInputView(view.findViewById(R.id.add_device_pair_new_access_key),
                view.findViewById(R.id.add_device_pair_new_access_key_input));
        mStatusStyledMessage = new StatusStyledColoredTextView(
                view.findViewById(R.id.add_device_pair_new_message));

        mPairButton = view.findViewById(R.id.add_device_pair_new_bluetooth_pair_button);
        mNextButton = view.findViewById(R.id.add_device_pair_new_control_next_button);
        mBackButton = view.findViewById(R.id.add_device_pair_new_control_back_button);
        mCancelButton = view.findViewById(R.id.add_device_pair_new_control_cancel_button);

    }

    private void initButtonsLogic(final View view) {
        NavController navController = Navigation.findNavController(view);
        mBackButton.setOnClickListener(l -> mViewModel.previousLook());
        mCancelButton.setOnClickListener(
                l -> {
                    navController.navigateUp();
                    navController.navigateUp();
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
        if (look == AddPairNewDeviceLook.BLUETOOTH) {
            mBackButton.setOnClickListener(l -> Navigation.findNavController(view).navigateUp());
        } else {
            mBackButton.setOnClickListener(l -> mViewModel.previousLook());
        }
    }

    private void initNextButtonLogic(final View view, final AddPairNewDeviceLook look) {
        this.setNextButtonActionStyle();
        if (look == AddPairNewDeviceLook.BLUETOOTH) {
            this.disableButton(mNextButton);
        }
        if (look == AddPairNewDeviceLook.ACCESS) {
            try {
                mNextButton.setOnClickListener(
                        l -> mViewModel.sendKey(mAccessKeyInput.getText()));
            } catch (InputNotAllowedException ignored) {
            }
        }
        if (look == AddPairNewDeviceLook.NETWORK) {
            mNextButton.setOnClickListener(l -> mViewModel.updateNetworkData());
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
        mStatusStyledMessage.setVisibility(View.VISIBLE);
        this.enableButton(mNextButton);
    }

    private void turnOnBluetooth() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        ActivityResultLauncher<Intent> bluetoothResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() != Activity.RESULT_OK) {
                        ToastProvider.showShort(requireContext(),
                                "You need to grant a bluetooth permission!");
                    }
                }
        );
        bluetoothResultLauncher.launch(enableBtIntent);
    }
}