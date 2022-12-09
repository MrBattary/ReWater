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

import michael.linker.rewater.R;
import michael.linker.rewater.data.model.IdNameModel;
import michael.linker.rewater.data.model.status.DetailedStatusModel;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.ui.advanced.devices.viewmodel.DevicesViewModel;
import michael.linker.rewater.ui.advanced.schedules.model.ScheduleUiModel;
import michael.linker.rewater.ui.elementary.parententity.ParentEntityView;
import michael.linker.rewater.ui.elementary.parententity.ParentScheduleInfoView;
import michael.linker.rewater.ui.elementary.status.DetailDescribedStatusView;

public class DeviceInfoFragment extends Fragment {
    private TextView mDeviceNameView;
    private ParentEntityView mParentScheduleView, mParentNetworkView;
    private DetailDescribedStatusView mDetailDescribedStatusView;
    private ParentScheduleInfoView mParentScheduleInfoView;
    private MaterialButton mManualWateringButton, mCancelButton;

    private DevicesViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        NavController navController = NavHostFragment.findNavController(this);
        ViewModelStoreOwner viewModelStoreOwner = navController.getViewModelStoreOwner(
                R.id.root_navigation_devices);
        mViewModel = new ViewModelProvider(viewModelStoreOwner).get(DevicesViewModel.class);

        return inflater.inflate(R.layout.fragment_devices_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.initFields(view);


        mViewModel.getDeviceName().observe(getViewLifecycleOwner(),
                name -> mDeviceNameView.setText(name));
        mViewModel.getDeviceStatus().observe(getViewLifecycleOwner(), this::initDeviceStatus);
        mViewModel.getParentScheduleModel().observe(getViewLifecycleOwner(),
                this::initParentScheduleData);
        mViewModel.getParentNetworkIdNameModel().observe(getViewLifecycleOwner(),
                this::initParentNetworkData);

        this.initButtons(view);
    }

    private void initFields(final View view) {
        mDeviceNameView = view.findViewById(R.id.device_info_name);

        mParentScheduleView = new ParentEntityView(
                view.findViewById(R.id.device_info_parents_schedule),
                StringsProvider.getString(R.string.parent_schedule_not_found));
        mParentNetworkView = new ParentEntityView(
                view.findViewById(R.id.device_info_parents_network),
                StringsProvider.getString(R.string.parent_network_not_found));

        mParentScheduleInfoView = new ParentScheduleInfoView(
                view.findViewById(R.id.device_info_schedule_information));
        mDetailDescribedStatusView = new DetailDescribedStatusView(
                view.findViewById(R.id.device_info_status));

        mManualWateringButton = view.findViewById(R.id.device_info_manual_watering_button);
        mCancelButton = view.findViewById(R.id.device_info_back_button);
    }

    private void initParentScheduleData(final ScheduleUiModel scheduleModel) {
        if (scheduleModel != null) {
            mParentScheduleView.setParentEntity(scheduleModel.getName());
            mParentScheduleInfoView.setWaterVolumeInfo(scheduleModel.getVolume());
            mParentScheduleInfoView.setWateringPeriodInfo(scheduleModel.getPeriod());
        } else {
            mParentScheduleView.clearParentEntity();
        }
    }

    private void initParentNetworkData(final IdNameModel networkModel) {
        if (networkModel != null) {
            mParentNetworkView.setParentEntity(networkModel.getName());
        } else {
            mParentNetworkView.clearParentEntity();
        }
    }

    private void initDeviceStatus(final DetailedStatusModel statusModel) {
        mDetailDescribedStatusView.showWater(statusModel.getWater());
        mDetailDescribedStatusView.showBattery(statusModel.getBattery());
    }

    private void initButtons(final View view) {
        NavController navController = Navigation.findNavController(view);
        mManualWateringButton.setOnClickListener(l -> navController.navigate(
                R.id.navigation_action_devices_info_to_devices_manual_watering));

        mCancelButton.setOnClickListener(l -> navController.navigateUp());
    }
}