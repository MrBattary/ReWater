package michael.linker.rewater.ui.advanced.schedules.view;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import michael.linker.rewater.R;
import michael.linker.rewater.data.res.DrawablesProvider;
import michael.linker.rewater.data.res.IntegersProvider;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.ui.advanced.devices.model.DeviceIdNameUiModel;
import michael.linker.rewater.ui.advanced.schedules.adapter.ScheduleAttachedDevicesItemAdapter;
import michael.linker.rewater.ui.advanced.schedules.viewmodel.SchedulesViewModelFailedException;
import michael.linker.rewater.ui.advanced.schedules.viewmodel.UpdateScheduleViewModel;
import michael.linker.rewater.ui.elementary.dialog.IDialog;
import michael.linker.rewater.ui.elementary.dialog.one.SingleChoiceDialogModel;
import michael.linker.rewater.ui.elementary.dialog.one.SingleChoiceInfoDialog;
import michael.linker.rewater.ui.elementary.dialog.two.TwoChoicesDialogModel;
import michael.linker.rewater.ui.elementary.dialog.two.TwoChoicesWarningDialog;
import michael.linker.rewater.ui.elementary.input.InputNotAllowedException;
import michael.linker.rewater.ui.elementary.input.composite.WaterVolumeMetricInputView;
import michael.linker.rewater.ui.elementary.input.composite.WateringPeriodInputView;
import michael.linker.rewater.ui.elementary.input.text.FocusableTextInputView;
import michael.linker.rewater.ui.elementary.input.text.IFocusableTextInputView;
import michael.linker.rewater.ui.elementary.toast.ToastProvider;

public class EditScheduleFragment extends Fragment {
    private IFocusableTextInputView mNameInput;
    private WaterVolumeMetricInputView mVolumeInputView;
    private WateringPeriodInputView mPeriodInputView;
    private RecyclerView mAttachedDevices;
    private AutoTransition mTransition;
    private MaterialButton mCreateButton, mCancelButton, mDeleteButton, mAttachDeviceButton;
    private IDialog mNotAllowedScheduleDialog, mNoAttachedDevicesDialog, mOnDeleteDialog;

    private UpdateScheduleViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        NavController navController = NavHostFragment.findNavController(this);
        ViewModelStoreOwner viewModelStoreOwner = navController.getViewModelStoreOwner(
                R.id.root_navigation_schedules);

        mViewModel = new ViewModelProvider(viewModelStoreOwner).get(UpdateScheduleViewModel.class);
        return inflater.inflate(R.layout.fragment_schedules_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.initFields(view);
        this.initFieldsData();
        this.initTransition();
        this.initInputLogics();
        this.initDialogs(view);
        this.initButtons(view);
        mViewModel.getAttachedDeviceList().observe(getViewLifecycleOwner(),
                attachedDeviceList -> initAttachedDeviceList(attachedDeviceList, view));
    }

    private void initFields(final View view) {
        mNameInput = new FocusableTextInputView(view.findViewById(R.id.edit_schedule_name),
                view.findViewById(R.id.edit_schedule_name_input));
        mVolumeInputView = new WaterVolumeMetricInputView(
                view.findViewById(R.id.edit_schedule_volume_input));
        mPeriodInputView = new WateringPeriodInputView(
                view.findViewById(R.id.edit_schedule_period_input));
        mAttachedDevices = view.findViewById(R.id.edit_schedule_devices_recycler_view);
        mCreateButton = view.findViewById(R.id.edit_schedule_create_button);
        mCancelButton = view.findViewById(R.id.edit_schedule_cancel_button);
        mDeleteButton = view.findViewById(R.id.edit_schedule_delete_button);
        mAttachDeviceButton = view.findViewById(R.id.edit_schedule_attach_device_button);
    }

    private void initFieldsData() {
        mViewModel.getScheduleName().observe(getViewLifecycleOwner(),
                name -> mNameInput.setText(name));
        mViewModel.getLitres().observe(getViewLifecycleOwner(),
                l -> mVolumeInputView.setLitresVolume(l));
        mViewModel.getMillilitres().observe(getViewLifecycleOwner(),
                ml -> mVolumeInputView.setMillilitresVolume(ml));
        mViewModel.getDays().observe(getViewLifecycleOwner(),
                d -> mPeriodInputView.setDaysPeriod(d));
        mViewModel.getHours().observe(getViewLifecycleOwner(),
                h -> mPeriodInputView.setHoursPeriod(h));
        mViewModel.getMinutes().observe(getViewLifecycleOwner(),
                m -> mPeriodInputView.setMinutesPeriod(m));
    }

    private void initTransition() {
        mTransition = new AutoTransition();
        mTransition.setDuration(IntegersProvider.getInteger(R.integer.transition_animation_time));
    }

    private void initInputLogics() {
        this.initNameInputLogic();
        this.initVolumeInputLogic();
        this.initPeriodInputLogic();
    }

    private void initNameInputLogic() {
        mViewModel.getAlreadyTakenSchedulesNames().observe(getViewLifecycleOwner(),
                list -> mNameInput.setBlacklist(list,
                        StringsProvider.getString(R.string.input_error_heading_taken)));
        mNameInput.setMaxLimit(IntegersProvider.getInteger(R.integer.input_max_limit_header),
                StringsProvider.getString(R.string.input_error_heading_overflow));
        mNameInput.setMinLimit(IntegersProvider.getInteger(R.integer.input_min_limit_header),
                StringsProvider.getString(R.string.input_error_heading_lack));
        mNameInput.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                this.saveName();
            }
        });
    }

    private void initVolumeInputLogic() {
        mVolumeInputView.setLitresInputOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                this.saveLitres();
            }
        });
        mVolumeInputView.setMillilitresInputOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                this.saveMillilitres();
            }
        });
    }

    private void initPeriodInputLogic() {
        mPeriodInputView.setDaysInputOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                this.saveDays();
            }
        });
        mPeriodInputView.setHoursInputOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                this.saveHours();
            }
        });
        mPeriodInputView.setMinutesInputOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                this.saveMinutes();
            }
        });
    }

    private void initDialogs(final View view) {
        final NavController navController = Navigation.findNavController(view);
        mNotAllowedScheduleDialog = new SingleChoiceInfoDialog(requireContext(),
                new SingleChoiceDialogModel(
                        DrawablesProvider.getDrawable(R.drawable.ic_warning),
                        StringsProvider.getString(R.string.title_warning),
                        StringsProvider.getString(
                                R.string.dialog_insufficient_volume_or_period),
                        StringsProvider.getString(R.string.button_ok)
                ),
                (dialogInterface, i) -> dialogInterface.cancel());

        mNoAttachedDevicesDialog = new TwoChoicesWarningDialog(requireContext(),
                new TwoChoicesDialogModel(
                        DrawablesProvider.getDrawable(R.drawable.ic_warning),
                        StringsProvider.getString(R.string.title_warning),
                        StringsProvider.getString(R.string.dialog_no_attached_devices_on_save),
                        StringsProvider.getString(R.string.button_save_anyway),
                        StringsProvider.getString(R.string.button_cancel)
                ),
                (dialogInterface, i) -> {
                    try {
                        mViewModel.commitAndUpdateSchedule();
                        navController.navigateUp();
                    } catch (SchedulesViewModelFailedException e) {
                        ToastProvider.showShort(requireContext(), e.getMessage());
                    }
                },
                (dialogInterface, i) -> dialogInterface.cancel());

        mOnDeleteDialog = new TwoChoicesWarningDialog(requireContext(),
                new TwoChoicesDialogModel(
                        DrawablesProvider.getDrawable(R.drawable.ic_warning),
                        StringsProvider.getString(R.string.title_warning),
                        StringsProvider.getString(R.string.dialog_delete_schedule),
                        StringsProvider.getString(R.string.button_delete),
                        StringsProvider.getString(R.string.button_cancel)
                ),
                (dialogInterface, i) -> {
                    mViewModel.commitAndDeleteSchedule();
                    navController.navigateUp();
                },
                (dialogInterface, i) -> dialogInterface.cancel());
    }

    private void initButtons(final View view) {
        final NavController navController = Navigation.findNavController(view);

        mAttachDeviceButton.setOnClickListener(l -> navController.navigate(
                R.id.navigation_action_schedules_edit_to_schedules_devices));
        mCreateButton.setOnClickListener(l -> {
            this.saveData();
            if (mViewModel.isProvidedDataCorrect()) {
                if (mViewModel.getAttachedDeviceList().getValue() != null
                        && mViewModel.getAttachedDeviceList().getValue().size() == 0) {
                    mNoAttachedDevicesDialog.show();
                } else {
                    try {
                        mViewModel.commitAndUpdateSchedule();
                        navController.navigateUp();
                    } catch (SchedulesViewModelFailedException e) {
                        ToastProvider.showShort(requireContext(), e.getMessage());
                    }
                }
            } else {
                mNotAllowedScheduleDialog.show();
            }
        });
        mDeleteButton.setOnClickListener(l -> mOnDeleteDialog.show());
        mCancelButton.setOnClickListener(l -> navController.navigateUp());
    }

    private void saveData() {
        this.saveName();

        this.saveLitres();
        this.saveMillilitres();

        this.saveDays();
        this.saveHours();
        this.saveMinutes();
    }

    private void saveName() {
        try {
            mViewModel.setScheduleName(mNameInput.getText());
        } catch (InputNotAllowedException ignored) {
        }
    }

    private void saveLitres() {
        try {
            mViewModel.setLitres(mVolumeInputView.getLitresVolume());
        } catch (InputNotAllowedException ignored) {
        }
    }

    private void saveMillilitres() {
        try {
            mViewModel.setMillilitres(mVolumeInputView.getMillilitresVolume());
        } catch (InputNotAllowedException ignored) {
        }
    }

    private void saveDays() {
        try {
            mViewModel.setDays(mPeriodInputView.getDaysPeriod());
        } catch (InputNotAllowedException ignored) {
        }
    }

    private void saveHours() {
        try {
            mViewModel.setHours(mPeriodInputView.getHoursPeriod());
        } catch (InputNotAllowedException ignored) {
        }
    }

    private void saveMinutes() {
        try {
            mViewModel.setMinutes(mPeriodInputView.getMinutesPeriod());
        } catch (InputNotAllowedException ignored) {
        }
    }

    private void initAttachedDeviceList(final List<DeviceIdNameUiModel> attachedDeviceList,
            final View view) {
        mAttachedDevices.setLayoutManager(new LinearLayoutManager(getContext()));
        TransitionManager.beginDelayedTransition(view.findViewById(R.id.schedules_edit_schedule),
                mTransition);
        mAttachedDevices.setAdapter(
                new ScheduleAttachedDevicesItemAdapter(
                        getContext(),
                        mViewModel,
                        attachedDeviceList));
    }

}