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
import michael.linker.rewater.data.repository.devices.model.EditableDeviceModel;
import michael.linker.rewater.data.repository.networks.model.CompactNetworkModel;
import michael.linker.rewater.data.repository.schedules.model.CompactScheduleModel;
import michael.linker.rewater.data.res.DrawablesProvider;
import michael.linker.rewater.data.res.IntegersProvider;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.ui.advanced.devices.viewmodel.DevicesViewModel;
import michael.linker.rewater.ui.elementary.dialog.IDialog;
import michael.linker.rewater.ui.elementary.dialog.TwoChoicesWarningDialog;
import michael.linker.rewater.ui.elementary.input.text.ITextInputView;
import michael.linker.rewater.ui.elementary.input.text.TextInputView;
import michael.linker.rewater.ui.elementary.parententity.ParentEntityView;
import michael.linker.rewater.ui.model.TwoChoicesWarningDialogModel;

public class EditDeviceFragment extends Fragment {
    private ITextInputView mNameInput;
    private ViewGroup mParentsExistsViewGroup, mParentsNotFoundViewGroup;
    private ParentEntityView mParentScheduleView, mParentNetworkView;
    private TextView mWaterTextInformationView, mPeriodTextInformationView;
    private MaterialButton mAttachButton, mDetachButton, mDeleteButton, mSaveButton, mCancelButton;
    private IDialog mOnDetachDialog, mOnDeleteDialog;
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
        mViewModel.getEditableDeviceModel().observe(getViewLifecycleOwner(), model -> {
            this.initInputs(model);
            this.initParentArea(model);
        });
        mViewModel.getParentScheduleModel().observe(getViewLifecycleOwner(),
                this::initParentScheduleData);
        mViewModel.getParentNetworkModel().observe(getViewLifecycleOwner(),
                this::initParentNetworkData);
        mViewModel.getEditableDeviceId().observe(getViewLifecycleOwner(), id -> {
            this.initDialogs(view, id);
            this.initButtons(view, id);
        });
    }

    private void initFields(final View view) {
        mParentsExistsViewGroup = view.findViewById(R.id.edit_device_parents_exists);
        mParentsNotFoundViewGroup = view.findViewById(R.id.edit_device_parents_not_found);
        mNameInput = new TextInputView(view.findViewById(R.id.edit_device_name),
                view.findViewById(R.id.edit_device_name_input));
        mParentScheduleView = new ParentEntityView(
                view.findViewById(R.id.edit_device_parents_schedule),
                StringsProvider.getString(R.string.parent_schedule_not_found));
        mParentNetworkView = new ParentEntityView(
                view.findViewById(R.id.edit_device_parents_network),
                StringsProvider.getString(R.string.parent_network_not_found));
        mWaterTextInformationView = view.findViewById(
                R.id.edit_device_parents_schedule_information_water);
        mPeriodTextInformationView = view.findViewById(
                R.id.edit_device_parents_schedule_information_period);
        mAttachButton = view.findViewById(R.id.edit_device_attach_schedule_button);
        mDetachButton = view.findViewById(R.id.edit_device_detach_schedule_button);
        mDeleteButton = view.findViewById(R.id.edit_device_delete_button);
        mSaveButton = view.findViewById(R.id.edit_device_save_button);
        mCancelButton = view.findViewById(R.id.edit_device_cancel_button);
    }

    private void initInputs(final EditableDeviceModel editableModel) {
        List<String> alreadyTakenNetworksNames =
                mViewModel.getAlreadyTakenDeviceNames().getValue();
        List<String> alreadyTakenNetworkNamesExceptThis = Collections.emptyList();
        if (alreadyTakenNetworksNames != null) {
            alreadyTakenNetworkNamesExceptThis = alreadyTakenNetworksNames
                    .stream()
                    .filter(heading -> !Objects.equals(heading, editableModel.getName()))
                    .collect(Collectors.toList());
        }

        mNameInput.setBlacklist(alreadyTakenNetworkNamesExceptThis,
                StringsProvider.getString(R.string.input_error_heading_taken));
        mNameInput.setMaxLimit(IntegersProvider.getInteger(R.integer.input_max_limit_header),
                StringsProvider.getString(R.string.input_error_heading_overflow));
        mNameInput.setMinLimit(IntegersProvider.getInteger(R.integer.input_min_limit_header),
                StringsProvider.getString(R.string.input_error_heading_lack));
        mNameInput.setText(editableModel.getName());
    }

    private void initParentArea(final EditableDeviceModel compactModel) {
        if (compactModel.getParentScheduleId() == null
                && compactModel.getParentNetworkId() == null) {
            mParentsNotFoundViewGroup.setVisibility(View.VISIBLE);
            mParentsExistsViewGroup.setVisibility(View.GONE);
        } else {
            mParentsNotFoundViewGroup.setVisibility(View.GONE);
            mParentsExistsViewGroup.setVisibility(View.VISIBLE);
        }
    }

    private void initParentScheduleData(final CompactScheduleModel compactScheduleModel) {
        if (compactScheduleModel != null) {
            mParentScheduleView.setParentEntity(compactScheduleModel.getName());
            mWaterTextInformationView.setText(compactScheduleModel.getVolume().formatToCompact());
            mPeriodTextInformationView.setText(compactScheduleModel.getPeriod().formatToCompact());
        } else {
            mParentScheduleView.clearParentEntity();
        }
    }

    private void initParentNetworkData(final CompactNetworkModel compactNetworkModel) {
        if (compactNetworkModel != null) {
            mParentNetworkView.setParentEntity(compactNetworkModel.getHeading());
        } else {
            mParentNetworkView.clearParentEntity();
        }
    }

    private void initDialogs(final View view, final String id) {
        mOnDeleteDialog = new TwoChoicesWarningDialog(requireContext(),
                new TwoChoicesWarningDialogModel(
                        DrawablesProvider.getDrawable(R.drawable.ic_warning),
                        StringsProvider.getString(R.string.title_warning),
                        StringsProvider.getString(R.string.dialog_delete_device_schedule),
                        StringsProvider.getString(R.string.button_delete),
                        StringsProvider.getString(R.string.button_cancel)
                ),
                (dialogInterface, i) -> {
                    mViewModel.removeDevice(id);
                    dialogInterface.cancel();
                    Navigation.findNavController(view).navigateUp();
                },
                (dialogInterface, i) -> dialogInterface.cancel());
    }

    private void initButtons(final View view, final String id) {
        mDeleteButton.setOnClickListener(l -> mOnDeleteDialog.show());
        mDetachButton.setOnClickListener(l -> mViewModel.detachParents());
        mCancelButton.setOnClickListener(l -> Navigation.findNavController(view).navigateUp());
    }
}