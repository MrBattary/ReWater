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

import com.google.android.material.button.MaterialButton;

import java.util.List;

import michael.linker.rewater.R;
import michael.linker.rewater.data.res.DrawablesProvider;
import michael.linker.rewater.data.res.IntegersProvider;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.ui.advanced.devices.model.DeviceIdNameUiModel;
import michael.linker.rewater.ui.advanced.schedules.adapter.ScheduleAttachedDevicesItemAdapter;
import michael.linker.rewater.ui.advanced.schedules.viewmodel.SchedulesViewModel;
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

public class AddScheduleFragment extends Fragment {
    private IFocusableTextInputView mNameInput;
    private WaterVolumeMetricInputView mVolumeInputView;
    private WateringPeriodInputView mPeriodInputView;
    private RecyclerView mAttachedDevices;
    private MaterialButton mCreateButton, mCancelButton, mAttachDeviceButton;
    private IDialog mNotAllowedScheduleDialog, mNoAttachedDevicesDialog;

    private UpdateScheduleViewModel mViewModel;
    private SchedulesViewModel mParentViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        NavController navController = NavHostFragment.findNavController(this);
        ViewModelStoreOwner viewModelStoreOwner = navController.getViewModelStoreOwner(
                R.id.root_navigation_schedules);

        mViewModel = new ViewModelProvider(this).get(UpdateScheduleViewModel.class);
        mParentViewModel = new ViewModelProvider(viewModelStoreOwner).get(SchedulesViewModel.class);

        return inflater.inflate(R.layout.fragment_schedules_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.initViewModel();
        this.initFields(view);
        this.initInputLogics();
        this.initDialogs(view);
        mViewModel.getAttachedDeviceList().observe(getViewLifecycleOwner(),
                attachedDeviceList -> {
                    this.initButtons(view, attachedDeviceList);
                    this.initAttachedDeviceList(attachedDeviceList);
                });

    }

    private void initViewModel() {
        mParentViewModel.getParentNetworkId().observe(getViewLifecycleOwner(),
                id -> mViewModel.setParentNetworkId(id));
    }

    private void initFields(final View view) {
        mNameInput = new FocusableTextInputView(view.findViewById(R.id.add_schedule_name),
                view.findViewById(R.id.add_schedule_name_input));
        mVolumeInputView = new WaterVolumeMetricInputView(
                view.findViewById(R.id.add_schedule_volume_input));
        mPeriodInputView = new WateringPeriodInputView(
                view.findViewById(R.id.add_schedule_period_input));
        mAttachedDevices = view.findViewById(R.id.add_schedule_devices_recycler_view);
        mCreateButton = view.findViewById(R.id.add_schedule_create_button);
        mCancelButton = view.findViewById(R.id.add_schedule_cancel_button);
        mAttachDeviceButton = view.findViewById(R.id.add_schedule_attach_device_button);
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
                try {
                    mViewModel.setScheduleName(mNameInput.getText());
                } catch (InputNotAllowedException ignored) {

                }
            }
        });
    }

    private void initVolumeInputLogic() {
        mVolumeInputView.setLitresInputOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                try {
                    mViewModel.setLitres(mVolumeInputView.getLitresVolume());
                } catch (InputNotAllowedException ignored) {
                }
            }
        });
        mVolumeInputView.setMillilitresInputOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                try {
                    mViewModel.setMillilitres(mVolumeInputView.getMillilitresVolume());
                } catch (InputNotAllowedException ignored) {
                }
            }
        });
    }

    private void initPeriodInputLogic() {
        mPeriodInputView.setDaysInputOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                try {
                    mViewModel.setDays(mPeriodInputView.getDaysPeriod());
                } catch (InputNotAllowedException ignored) {
                }
            }
        });
        mPeriodInputView.setHoursInputOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                try {
                    mViewModel.setHours(mPeriodInputView.getHoursPeriod());
                } catch (InputNotAllowedException ignored) {
                }
            }
        });
        mPeriodInputView.setMinutesInputOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                try {
                    mViewModel.setMinutes(mPeriodInputView.getMinutesPeriod());
                } catch (InputNotAllowedException ignored) {
                }
            }
        });
    }

    private void initDialogs(final View view) {
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
                        StringsProvider.getString(R.string.dialog_no_attached_devices_on_create),
                        StringsProvider.getString(R.string.button_create_anyway),
                        StringsProvider.getString(R.string.button_cancel)
                ),
                (dialogInterface, i) -> {
                    mViewModel.commitAndCreateSchedule();
                    Navigation.findNavController(view).navigateUp();
                },
                (dialogInterface, i) -> dialogInterface.cancel());
    }

    private void initButtons(final View view, final List<DeviceIdNameUiModel> attachedDeviceList) {
        final NavController navController = Navigation.findNavController(view);
        mAttachDeviceButton.setOnClickListener(l -> {
            navController.navigate(R.id.navigation_action_schedules_add_to_schedules_devices);
        });
        mCreateButton.setOnClickListener(l -> {
            if (mViewModel.isProvidedDataCorrect()) {
                if (attachedDeviceList.size() == 0) {
                    mNoAttachedDevicesDialog.show();
                } else {
                    mViewModel.commitAndCreateSchedule();
                    navController.navigateUp();
                }
            } else {
                mNotAllowedScheduleDialog.show();
            }
        });

        mCancelButton.setOnClickListener(l -> navController.navigateUp());
    }

    private void initAttachedDeviceList(final List<DeviceIdNameUiModel> attachedDeviceList) {
        mAttachedDevices.setLayoutManager(new LinearLayoutManager(getContext()));
        mAttachedDevices.setAdapter(
                new ScheduleAttachedDevicesItemAdapter(
                        getContext(),
                        mViewModel,
                        attachedDeviceList));
    }
}