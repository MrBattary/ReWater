package michael.linker.rewater.ui.advanced.devices.view;

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

import com.google.android.material.button.MaterialButton;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import michael.linker.rewater.R;
import michael.linker.rewater.data.repository.networks.model.NetworkModel;
import michael.linker.rewater.data.repository.schedules.model.ScheduleModel;
import michael.linker.rewater.data.res.DrawablesProvider;
import michael.linker.rewater.data.res.IntegersProvider;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.ui.advanced.devices.model.DeviceInfoModel;
import michael.linker.rewater.ui.advanced.devices.viewmodel.DevicesViewModel;
import michael.linker.rewater.ui.advanced.devices.viewmodel.DevicesViewModelFailedException;
import michael.linker.rewater.ui.elementary.dialog.IDialog;
import michael.linker.rewater.ui.elementary.dialog.TwoChoicesWarningDialog;
import michael.linker.rewater.ui.elementary.input.InputNotAllowedException;
import michael.linker.rewater.ui.elementary.input.text.ITextInputView;
import michael.linker.rewater.ui.elementary.input.text.TextInputView;
import michael.linker.rewater.ui.elementary.parententity.ParentEntityView;
import michael.linker.rewater.ui.elementary.toast.ToastProvider;
import michael.linker.rewater.ui.model.TwoChoicesWarningDialogModel;

public class AddDeviceFragment extends Fragment {
    private ITextInputView mNameInput;
    private ViewGroup mParentsExistsViewGroup, mParentsNotFoundViewGroup;
    private ParentEntityView mParentScheduleView, mParentNetworkView;
    private TextView mWaterTextInformationView, mPeriodTextInformationView;
    private MaterialButton mAttachButton, mDetachButton, mCreateButton, mBackButton, mCancelButton;
    private IDialog mOnNoScheduleAddDialog;

    private DevicesViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        NavController navController = NavHostFragment.findNavController(this);
        ViewModelStoreOwner viewModelStoreOwner = navController.getViewModelStoreOwner(
                R.id.root_navigation_devices);
        mViewModel = new ViewModelProvider(viewModelStoreOwner).get(DevicesViewModel.class);
        return inflater.inflate(R.layout.fragment_devices_add, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.initFields(view);

        mViewModel.getDeviceInfoModel().observe(getViewLifecycleOwner(), this::initInputs);
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
        mParentsExistsViewGroup = view.findViewById(R.id.add_device_parents_exists);
        mParentsNotFoundViewGroup = view.findViewById(R.id.add_device_parents_not_found);
        mNameInput = new TextInputView(view.findViewById(R.id.add_device_name),
                view.findViewById(R.id.add_device_name_input));
        mParentScheduleView = new ParentEntityView(
                view.findViewById(R.id.add_device_parents_schedule),
                StringsProvider.getString(R.string.parent_schedule_not_found));
        mParentNetworkView = new ParentEntityView(
                view.findViewById(R.id.add_device_parents_network),
                StringsProvider.getString(R.string.parent_network_not_found));
        mWaterTextInformationView = view.findViewById(
                R.id.add_device_parents_schedule_information_water);
        mPeriodTextInformationView = view.findViewById(
                R.id.add_device_parents_schedule_information_period);
        mAttachButton = view.findViewById(R.id.add_device_attach_schedule_button);
        mDetachButton = view.findViewById(R.id.add_device_detach_schedule_button);
        mCreateButton = view.findViewById(R.id.add_device_save_button);
        mBackButton = view.findViewById(R.id.add_device_back_button);
        mCancelButton = view.findViewById(R.id.add_device_cancel_button);
    }

    private void initInputs(final DeviceInfoModel editableModel) {
        List<String> alreadyTakenDevicesNames =
                mViewModel.getAlreadyTakenDeviceNames().getValue();
        List<String> alreadyTakenDevicesNamesExceptThis = Collections.emptyList();
        if (alreadyTakenDevicesNames != null) {
            alreadyTakenDevicesNamesExceptThis = alreadyTakenDevicesNames
                    .stream()
                    .filter(heading -> !Objects.equals(heading, editableModel.getName()))
                    .collect(Collectors.toList());
        }

        mNameInput.setBlacklist(alreadyTakenDevicesNamesExceptThis,
                StringsProvider.getString(R.string.input_error_heading_taken));
        mNameInput.setMaxLimit(IntegersProvider.getInteger(R.integer.input_max_limit_header),
                StringsProvider.getString(R.string.input_error_heading_overflow));
        mNameInput.setMinLimit(IntegersProvider.getInteger(R.integer.input_min_limit_header),
                StringsProvider.getString(R.string.input_error_heading_lack));
        mNameInput.setText(editableModel.getName());
    }

    private void initParentArea(final ScheduleModel scheduleModel,
            final NetworkModel networkModel) {
        if (scheduleModel == null && networkModel == null) {
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
            mWaterTextInformationView.setText(scheduleModel.getVolume().formatToCompact());
            mPeriodTextInformationView.setText(scheduleModel.getPeriod().formatToCompact());
        } else {
            mParentScheduleView.clearParentEntity();
        }
    }

    private void initParentNetworkData(final NetworkModel networkModel) {
        if (networkModel != null) {
            mParentNetworkView.setParentEntity(networkModel.getHeading());
        } else {
            mParentNetworkView.clearParentEntity();
        }
    }

    private void initDialogs(final View view) {
        mOnNoScheduleAddDialog = new TwoChoicesWarningDialog(requireContext(),
                new TwoChoicesWarningDialogModel(
                        DrawablesProvider.getDrawable(R.drawable.ic_warning),
                        StringsProvider.getString(R.string.title_warning),
                        StringsProvider.getString(R.string.dialog_no_attached_schedule_on_add),
                        StringsProvider.getString(R.string.button_add_anyway),
                        StringsProvider.getString(R.string.button_cancel)
                ),
                (dialogInterface, i) -> {
                    try {
                        mViewModel.commitAndAddNewDevice(new DeviceInfoModel(
                                mNameInput.getText()
                        ));
                        this.finishAdding(Navigation.findNavController(view));
                    } catch (DevicesViewModelFailedException e) {
                        ToastProvider.showShort(requireContext(), e.getMessage());
                    } catch (InputNotAllowedException ignored) {
                    }
                },
                (dialogInterface, i) -> dialogInterface.cancel());
    }

    private void initButtons(final View view, final ScheduleModel scheduleModel) {
        final NavController navController = Navigation.findNavController(view);

        mDetachButton.setOnClickListener(l -> mViewModel.detachParents());
        mAttachButton.setOnClickListener(l -> navController.navigate(
                R.id.navigation_action_devices_add_to_devices_schedules));
        mCreateButton.setOnClickListener(l -> {
            try {
                if (scheduleModel == null || scheduleModel.getId() == null) {
                    mOnNoScheduleAddDialog.show();
                } else {
                    mViewModel.commitAndAddNewDevice(new DeviceInfoModel(
                            mNameInput.getText()
                    ));
                    this.finishAdding(navController);
                }
            } catch (DevicesViewModelFailedException e) {
                ToastProvider.showShort(requireContext(), e.getMessage());
            } catch (InputNotAllowedException ignored) {
            }
        });

        mBackButton.setOnClickListener(l -> Navigation.findNavController(view).navigateUp());
        mCancelButton.setOnClickListener(l -> this.finishAdding(navController));
    }

    private void finishAdding(final NavController navController) {
        navController.navigateUp();
        navController.navigateUp();
        navController.navigateUp();
    }
}