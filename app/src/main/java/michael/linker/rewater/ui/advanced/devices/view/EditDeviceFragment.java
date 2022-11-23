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

import java.util.List;

import michael.linker.rewater.R;
import michael.linker.rewater.data.repository.networks.model.NetworkRepositoryModel;
import michael.linker.rewater.data.repository.schedules.model.ScheduleModel;
import michael.linker.rewater.data.res.DrawablesProvider;
import michael.linker.rewater.data.res.IntegersProvider;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.ui.advanced.devices.model.DeviceInfoModel;
import michael.linker.rewater.ui.advanced.devices.viewmodel.DevicesViewModel;
import michael.linker.rewater.ui.advanced.devices.viewmodel.DevicesViewModelFailedException;
import michael.linker.rewater.ui.elementary.dialog.IDialog;
import michael.linker.rewater.ui.elementary.dialog.two.TwoChoicesWarningDialog;
import michael.linker.rewater.ui.elementary.input.InputNotAllowedException;
import michael.linker.rewater.ui.elementary.input.text.FocusableTextInputView;
import michael.linker.rewater.ui.elementary.input.text.IFocusableTextInputView;
import michael.linker.rewater.ui.elementary.parententity.ParentEntityView;
import michael.linker.rewater.ui.elementary.parententity.ParentScheduleInfoView;
import michael.linker.rewater.ui.elementary.toast.ToastProvider;
import michael.linker.rewater.ui.elementary.dialog.two.TwoChoicesDialogModel;

public class EditDeviceFragment extends Fragment {
    private boolean initialized = false;
    private IFocusableTextInputView mNameInput;
    private ViewGroup mParentsExistsViewGroup, mParentsNotFoundViewGroup;
    private ParentEntityView mParentScheduleView, mParentNetworkView;
    private ParentScheduleInfoView mParentScheduleInfoView;
    private MaterialButton mAttachButton, mDetachButton, mDeleteButton, mSaveButton, mCancelButton;
    private IDialog mOnNoScheduleSaveDialog, mOnDeleteDialog;

    private DevicesViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        NavController navController = NavHostFragment.findNavController(this);
        ViewModelStoreOwner viewModelStoreOwner = navController.getViewModelStoreOwner(
                R.id.root_navigation_devices);
        mViewModel = new ViewModelProvider(viewModelStoreOwner).get(DevicesViewModel.class);

        return inflater.inflate(R.layout.fragment_devices_edit, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.initFields(view);

        mViewModel.getAlreadyTakenDeviceNames().observe(getViewLifecycleOwner(),
                this::initInputsLogic);
        mViewModel.getDeviceName().observe(getViewLifecycleOwner(), this::initInputs);
        mViewModel.getParentScheduleModel().observe(getViewLifecycleOwner(), m -> {
            this.initParentScheduleData(m);
            this.initButtons(view, m);
        });
        mViewModel.getParentNetworkModel().observe(getViewLifecycleOwner(),
                this::initParentNetworkData);
        mViewModel.getParentScheduleModel().observe(getViewLifecycleOwner(),
                sm -> mViewModel.getParentNetworkModel().observe(getViewLifecycleOwner(),
                        nm -> this.initParentArea(sm, nm)));
        mViewModel.getDeviceId().observe(getViewLifecycleOwner(),
                id -> this.initDialogs(view));
    }

    private void initFields(final View view) {
        mParentsExistsViewGroup = view.findViewById(R.id.edit_device_parents_exists);
        mParentsNotFoundViewGroup = view.findViewById(R.id.edit_device_parents_not_found);
        mNameInput = new FocusableTextInputView(view.findViewById(R.id.edit_device_name),
                view.findViewById(R.id.edit_device_name_input));
        mParentScheduleView = new ParentEntityView(
                view.findViewById(R.id.edit_device_parents_schedule),
                StringsProvider.getString(R.string.parent_schedule_not_found));
        mParentNetworkView = new ParentEntityView(
                view.findViewById(R.id.edit_device_parents_network),
                StringsProvider.getString(R.string.parent_network_not_found));
        mParentScheduleInfoView = new ParentScheduleInfoView(
                view.findViewById(R.id.edit_device_schedule_information));
        mAttachButton = view.findViewById(R.id.edit_device_attach_schedule_button);
        mDetachButton = view.findViewById(R.id.edit_device_detach_schedule_button);
        mDeleteButton = view.findViewById(R.id.edit_device_delete_button);
        mSaveButton = view.findViewById(R.id.edit_device_save_button);
        mCancelButton = view.findViewById(R.id.edit_device_cancel_button);
    }

    private void initInputsLogic(List<String> alreadyTakenDevicesNames) {
        mNameInput.setBlacklist(alreadyTakenDevicesNames,
                StringsProvider.getString(R.string.input_error_heading_taken));
        mNameInput.setMaxLimit(IntegersProvider.getInteger(R.integer.input_max_limit_header),
                StringsProvider.getString(R.string.input_error_heading_overflow));
        mNameInput.setMinLimit(IntegersProvider.getInteger(R.integer.input_min_limit_header),
                StringsProvider.getString(R.string.input_error_heading_lack));
        mNameInput.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                this.storeInputs();
            }
        });
    }

    private void initInputs(final String deviceName) {
        if (!initialized && mNameInput.isTextBlacklisted(deviceName)) {
            mNameInput.removeFromBlackList(deviceName);
            initialized = true;
        }
        mNameInput.setText(deviceName);
    }

    private void initParentArea(final ScheduleModel scheduleModel,
            final NetworkRepositoryModel networkRepositoryModel) {
        if (scheduleModel == null && networkRepositoryModel == null) {
            mParentsNotFoundViewGroup.setVisibility(View.VISIBLE);
            mParentsExistsViewGroup.setVisibility(View.GONE);
        } else {
            mParentsNotFoundViewGroup.setVisibility(View.GONE);
            mParentsExistsViewGroup.setVisibility(View.VISIBLE);
        }
    }

    private void initParentScheduleData(final ScheduleModel scheduleModel) {
        if (scheduleModel != null) {
            mParentScheduleView.setParentEntity(scheduleModel.getName());
            mParentScheduleInfoView.setWaterVolumeInfo(scheduleModel.getVolume());
            mParentScheduleInfoView.setWateringPeriodInfo(scheduleModel.getPeriod());
        } else {
            mParentScheduleView.clearParentEntity();
        }
    }

    private void initParentNetworkData(final NetworkRepositoryModel networkRepositoryModel) {
        if (networkRepositoryModel != null) {
            mParentNetworkView.setParentEntity(networkRepositoryModel.getName());
        } else {
            mParentNetworkView.clearParentEntity();
        }
    }

    private void initDialogs(final View view) {
        mOnDeleteDialog = new TwoChoicesWarningDialog(requireContext(),
                new TwoChoicesDialogModel(
                        DrawablesProvider.getDrawable(R.drawable.ic_warning),
                        StringsProvider.getString(R.string.title_warning),
                        StringsProvider.getString(R.string.dialog_delete_device),
                        StringsProvider.getString(R.string.button_delete),
                        StringsProvider.getString(R.string.button_cancel)
                ),
                (dialogInterface, i) -> {
                    mViewModel.removeDevice();
                    dialogInterface.cancel();
                    Navigation.findNavController(view).navigateUp();
                },
                (dialogInterface, i) -> dialogInterface.cancel());

        mOnNoScheduleSaveDialog = new TwoChoicesWarningDialog(requireContext(),
                new TwoChoicesDialogModel(
                        DrawablesProvider.getDrawable(R.drawable.ic_warning),
                        StringsProvider.getString(R.string.title_warning),
                        StringsProvider.getString(R.string.dialog_no_attached_schedule_on_save),
                        StringsProvider.getString(R.string.button_save_anyway),
                        StringsProvider.getString(R.string.button_cancel)
                ),
                (dialogInterface, i) -> {
                    try {
                        mViewModel.commitAndUpdateDevice(new DeviceInfoModel(
                                mNameInput.getText()
                        ));
                        Navigation.findNavController(view).navigateUp();
                    } catch (DevicesViewModelFailedException e) {
                        ToastProvider.showShort(requireContext(), e.getMessage());
                    } catch (InputNotAllowedException ignored) {
                    }
                },
                (dialogInterface, i) -> dialogInterface.cancel());
    }

    private void initButtons(final View view, final ScheduleModel scheduleModel) {
        mDeleteButton.setOnClickListener(l -> {
            this.storeInputs();
            mOnDeleteDialog.show();
        });
        mDetachButton.setOnClickListener(l -> {
            this.storeInputs();
            mViewModel.detachParents();
        });
        mAttachButton.setOnClickListener(l -> {
            this.storeInputs();
            Navigation.findNavController(view).navigate(
                    R.id.navigation_action_devices_edit_to_devices_schedules);
        });
        mSaveButton.setOnClickListener(l -> {
            try {
                this.storeInputs();
                if (scheduleModel == null || scheduleModel.getId() == null) {
                    mOnNoScheduleSaveDialog.show();
                } else {
                    mViewModel.commitAndUpdateDevice(new DeviceInfoModel(
                            mNameInput.getText()
                    ));
                    Navigation.findNavController(view).navigateUp();
                }
            } catch (DevicesViewModelFailedException e) {
                ToastProvider.showShort(requireContext(), e.getMessage());
            } catch (InputNotAllowedException ignored) {
            }
        });

        mCancelButton.setOnClickListener(l -> Navigation.findNavController(view).navigateUp());
    }

    private void storeInputs() {
        mViewModel.setDeviceName(mNameInput.getTextForce());
    }
}