package michael.linker.rewater.ui.advanced.devices.view;

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

import michael.linker.rewater.R;
import michael.linker.rewater.data.res.ColorsProvider;
import michael.linker.rewater.data.res.IntegersProvider;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.ui.advanced.devices.model.AddPairNewDeviceLook;
import michael.linker.rewater.ui.advanced.devices.model.AddPairNewDeviceRequest;
import michael.linker.rewater.ui.advanced.devices.model.RequestStatus;
import michael.linker.rewater.ui.advanced.devices.viewmodel.AddPairNewDeviceViewModel;
import michael.linker.rewater.ui.advanced.devices.viewmodel.DevicesViewModel;
import michael.linker.rewater.ui.elementary.input.InputNotAllowedException;
import michael.linker.rewater.ui.elementary.input.text.ITextInputView;
import michael.linker.rewater.ui.elementary.input.text.TextInputView;

public class AddPairNewDeviceFragment extends Fragment {
    private ViewGroup mBluetoothView, mAccessView, mNetworkView;
    private ITextInputView mAccessKeyInput;
    private TextView mErrorMessage, mSuccessMessage;
    private MaterialButton mPairButton, mNextButton, mBackButton, mCancelButton;
    private AddPairNewDeviceViewModel mPersonalViewModel;
    private DevicesViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        NavController navController = NavHostFragment.findNavController(this);
        ViewModelStoreOwner viewModelStoreOwner = navController.getViewModelStoreOwner(
                R.id.root_navigation_devices);

        mPersonalViewModel = new ViewModelProvider(this).get(AddPairNewDeviceViewModel.class);
        mViewModel = new ViewModelProvider(viewModelStoreOwner).get(DevicesViewModel.class);

        return inflater.inflate(R.layout.fragment_devices_add_pair_new, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.initFields(view);
        this.initButtonsLogic(view);

        mPersonalViewModel.getCurrentLook().observe(getViewLifecycleOwner(), currentLook -> {
            this.hideMessages();
            this.setVisibilityOfViewGroups(currentLook);
            this.initBackButtonLogic(view, currentLook);
            this.initNextButtonLogic(view, currentLook);

            AutoTransition autoTransition = new AutoTransition();
            autoTransition.setDuration(
                    IntegersProvider.getInteger(R.integer.transition_animation_time));
            TransitionManager.beginDelayedTransition(view.findViewById(R.id.devices_add_pair_new),
                    autoTransition);

            if (currentLook == AddPairNewDeviceLook.FINISH) {
                setSuccessMessage(StringsProvider.getString(R.string.pair_device_success));
            }
            mPersonalViewModel.getBluetoothConnected().observe(getViewLifecycleOwner(), request -> {
                this.enableButton(mPairButton);
                if (currentLook == AddPairNewDeviceLook.BLUETOOTH
                        && request.getStatus() == RequestStatus.OK) {
                    this.setMessage(view, request, R.string.pair_device_network_success,
                            R.string.pair_device_network_failure);
                    this.disableButton(mPairButton);
                    this.allowProceedToTheNextLook();
                }
            });
            mPersonalViewModel.getAccessKeyAccepted().observe(getViewLifecycleOwner(), request -> {
                if (currentLook == AddPairNewDeviceLook.ACCESS
                        && request.getStatus() == RequestStatus.OK) {
                    this.setMessage(view, request, R.string.pair_device_access_success,
                            R.string.pair_device_access_failure);
                    this.allowProceedToTheNextLook();
                }
            });
            mPersonalViewModel.getNetworkUpdated().observe(getViewLifecycleOwner(), request -> {
                if (currentLook == AddPairNewDeviceLook.NETWORK
                        && request.getStatus() == RequestStatus.OK) {
                    this.setMessage(view, request, R.string.pair_device_network_success,
                            R.string.pair_device_network_failure);
                    this.allowProceedToTheNextLook();
                }
            });
        });
    }

    private void initFields(final View view) {
        mBluetoothView = view.findViewById(R.id.devices_add_pair_new_bluetooth);
        mAccessView = view.findViewById(R.id.devices_add_pair_new_access);
        mNetworkView = view.findViewById(R.id.devices_add_pair_new_network);

        mAccessKeyInput = new TextInputView(view.findViewById(R.id.devices_add_pair_new_access_key),
                view.findViewById(R.id.devices_add_pair_new_access_key_input));
        mErrorMessage = view.findViewById(R.id.devices_add_pair_new_messages_error_message);
        mSuccessMessage = view.findViewById(R.id.devices_add_pair_new_messages_success_message);

        mPairButton = view.findViewById(R.id.devices_add_pair_new_bluetooth_pair_button);
        mNextButton = view.findViewById(R.id.devices_add_pair_new_control_next_button);
        mBackButton = view.findViewById(R.id.devices_add_pair_new_control_back_button);
        mCancelButton = view.findViewById(R.id.devices_add_pair_new_control_cancel_button);

    }

    private void initButtonsLogic(final View view) {
        NavController navController = Navigation.findNavController(view);
        mPairButton.setOnClickListener(l -> mPersonalViewModel.connectToDevice());
        mBackButton.setOnClickListener(l -> mPersonalViewModel.previousLook());
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
            mBackButton.setOnClickListener(l -> mPersonalViewModel.previousLook());
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
                        l -> mPersonalViewModel.sendKey(mAccessKeyInput.getText()));
            } catch (InputNotAllowedException ignored) {
            }
        }
        if (look == AddPairNewDeviceLook.NETWORK) {
            mNextButton.setOnClickListener(l -> mPersonalViewModel.updateNetworkData());
        }
        if (look == AddPairNewDeviceLook.FINISH) {
            mNextButton.setOnClickListener(l -> Navigation.findNavController(view).navigate(
                    R.id.navigation_action_devices_add_pair_new_to_devices_add));
        }
    }

    private void hideMessages() {
        mErrorMessage.setVisibility(View.GONE);
        mSuccessMessage.setVisibility(View.GONE);
    }

    private void setMessage(final View view, final AddPairNewDeviceRequest request,
            final int successId, final int failureId) {
        AutoTransition autoTransition = new AutoTransition();
        autoTransition.addTarget(view.findViewById(R.id.devices_add_pair_new_messages));
        autoTransition.addTarget(view.findViewById(R.id.devices_add_pair_new_controls));
        autoTransition.setDuration(
                IntegersProvider.getInteger(R.integer.transition_animation_time));
        TransitionManager.beginDelayedTransition(view.findViewById(R.id.devices_add_pair_new),
                autoTransition);

        if (request.getStatus() == RequestStatus.OK) {
            setSuccessMessage(StringsProvider.getString(successId));
        }
        if (request.getStatus() == RequestStatus.ERROR) {
            setErrorMessage(StringsProvider.getString(failureId));
        }
    }

    private void setSuccessMessage(final String successMessage) {
        mSuccessMessage.setText(successMessage);
        mSuccessMessage.setVisibility(View.VISIBLE);
        mErrorMessage.setVisibility(View.GONE);
    }

    private void setErrorMessage(final String errorMessage) {
        mErrorMessage.setText(errorMessage);
        mErrorMessage.setVisibility(View.VISIBLE);
        mSuccessMessage.setVisibility(View.GONE);
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
        mNextButton.setOnClickListener(l -> mPersonalViewModel.nextLook());
        this.enableButton(mNextButton);
    }
}