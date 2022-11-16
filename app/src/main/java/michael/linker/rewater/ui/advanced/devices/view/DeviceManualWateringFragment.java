package michael.linker.rewater.ui.advanced.devices.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;

import michael.linker.rewater.R;
import michael.linker.rewater.data.model.unit.WaterVolumeMetricModel;
import michael.linker.rewater.data.res.DrawablesProvider;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.ui.advanced.devices.viewmodel.DeviceManualWateringViewModel;
import michael.linker.rewater.ui.advanced.devices.viewmodel.DevicesViewModel;
import michael.linker.rewater.ui.advanced.devices.viewmodel.DevicesViewModelFailedException;
import michael.linker.rewater.ui.elementary.dialog.IDialog;
import michael.linker.rewater.ui.elementary.dialog.one.SingleChoiceInfoDialog;
import michael.linker.rewater.ui.elementary.dialog.one.SingleChoiceDialogModel;
import michael.linker.rewater.ui.elementary.dialog.two.TwoChoicesConfirmDialog;
import michael.linker.rewater.ui.elementary.dialog.two.TwoChoicesWarningDialog;
import michael.linker.rewater.ui.elementary.dialog.two.TwoChoicesDialogModel;
import michael.linker.rewater.ui.elementary.input.InputNotAllowedException;
import michael.linker.rewater.ui.elementary.input.composite.WaterVolumeMetricInputView;
import michael.linker.rewater.ui.elementary.toast.ToastProvider;

public class DeviceManualWateringFragment extends Fragment {
    private WaterVolumeMetricInputView mWaterVolumeMetricInputView;
    private MaterialButton mWaterButton, mCancelButton;
    private IDialog mZeroWaterDialog, mForceWateringDialog, mConfirmationWateringDialog;

    private DeviceManualWateringViewModel mViewModel;
    private DevicesViewModel mParentViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        NavController navController = NavHostFragment.findNavController(this);
        ViewModelStoreOwner viewModelStoreOwner = navController.getViewModelStoreOwner(
                R.id.root_navigation_devices);

        mViewModel = new ViewModelProvider(this).get(DeviceManualWateringViewModel.class);
        mParentViewModel = new ViewModelProvider(viewModelStoreOwner).get(DevicesViewModel.class);

        return inflater.inflate(R.layout.fragment_devices_manual_watering, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.initFields(view);
        this.initDialogs(view);
        this.initButtons(view);
        this.initInputs();

        mParentViewModel.getDeviceId().observe(getViewLifecycleOwner(),
                id -> mViewModel.setDeviceId(id));
        mViewModel.getWaterVolumeMetricData().observe(getViewLifecycleOwner(),
                metricModel -> initWateringDialog(view, metricModel));
    }

    private void initFields(final View view) {
        mWaterVolumeMetricInputView = new WaterVolumeMetricInputView(
                view.findViewById(R.id.device_manual_watering_volume_input));
        mWaterButton = view.findViewById(R.id.device_manual_watering_water_button);
        mCancelButton = view.findViewById(R.id.device_manual_watering_cancel_button);
    }

    private void initInputs() {
        mWaterVolumeMetricInputView.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                try {
                    mViewModel.setWaterVolumeMetricData(
                            mWaterVolumeMetricInputView.getWaterVolume());
                } catch (InputNotAllowedException ignored) {
                }
            }
        });
    }

    private void initWateringDialog(final View view, final WaterVolumeMetricModel model) {
        mConfirmationWateringDialog = new TwoChoicesConfirmDialog(requireContext(),
                new TwoChoicesDialogModel(
                        DrawablesProvider.getDrawable(R.drawable.ic_warning),
                        StringsProvider.getString(R.string.title_warning),
                        StringsProvider.getString(R.string.dialog_manual_watering_confirmation_1)
                                + model.formatToCompact() + StringsProvider.getString(
                                R.string.dialog_manual_watering_confirmation_2),
                        StringsProvider.getString(R.string.button_water),
                        StringsProvider.getString(R.string.button_cancel)
                ),
                (dialogInterface, i) -> {
                    try {
                        mViewModel.waterWithProvidedModel();
                        Navigation.findNavController(view).navigateUp();
                    } catch (DevicesViewModelFailedException e) {
                        ToastProvider.showShort(requireContext(), e.getMessage());
                    } catch (InputNotAllowedException ignored) {
                    }
                },
                (dialogInterface, i) -> dialogInterface.cancel());
    }

    private void initDialogs(final View view) {
        mZeroWaterDialog = new SingleChoiceInfoDialog(requireContext(),
                new SingleChoiceDialogModel(
                        DrawablesProvider.getDrawable(R.drawable.ic_warning),
                        StringsProvider.getString(R.string.title_warning),
                        StringsProvider.getString(R.string.dialog_manual_watering_zero_water),
                        StringsProvider.getString(R.string.button_ok)
                ),
                (dialogInterface, i) -> dialogInterface.cancel());

        mForceWateringDialog = new TwoChoicesWarningDialog(requireContext(),
                new TwoChoicesDialogModel(
                        DrawablesProvider.getDrawable(R.drawable.ic_warning),
                        StringsProvider.getString(R.string.title_warning),
                        StringsProvider.getString(R.string.dialog_manual_watering_overflow),
                        StringsProvider.getString(R.string.button_force_watering),
                        StringsProvider.getString(R.string.button_cancel)
                ),
                (dialogInterface, i) -> {
                    try {
                        mViewModel.forceWaterWithProvidedModel();
                        Navigation.findNavController(view).navigateUp();
                    } catch (DevicesViewModelFailedException e) {
                        ToastProvider.showShort(requireContext(), e.getMessage());
                    } catch (InputNotAllowedException ignored) {
                    }
                },
                (dialogInterface, i) -> dialogInterface.cancel());
    }

    private void initButtons(final View view) {
        final NavController navController = Navigation.findNavController(view);
        mWaterButton.setOnClickListener(l -> {
            try {
                mViewModel.setWaterVolumeMetricData(
                        mWaterVolumeMetricInputView.getWaterVolume());
                if (mViewModel.isProvidedModelNotZeroVolume()) {
                    mConfirmationWateringDialog.show();
                } else {
                    mZeroWaterDialog.show();
                }
            } catch (DevicesViewModelFailedException e) {
                mForceWateringDialog.show();
            } catch (InputNotAllowedException ignored) {
            }
        });

        mCancelButton.setOnClickListener(l -> navController.navigateUp());
    }
}