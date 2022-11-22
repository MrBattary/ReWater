package michael.linker.rewater.ui.advanced.schedules.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.List;

import michael.linker.rewater.R;
import michael.linker.rewater.ui.advanced.devices.model.DeviceIdNameUiModel;
import michael.linker.rewater.ui.advanced.schedules.viewmodel.UpdateScheduleViewModel;

public class ScheduleDevicesFragment extends Fragment {
    private ListView mListView;
    private ArrayAdapter<UnattachedDeviceItemModel> mAdapter;

    private UpdateScheduleViewModel mParentViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        NavController navController = NavHostFragment.findNavController(this);
        ViewModelStoreOwner viewModelStoreOwner = navController.getViewModelStoreOwner(
                R.id.root_navigation_schedules);
        mParentViewModel = new ViewModelProvider(viewModelStoreOwner).get(
                UpdateScheduleViewModel.class);

        return inflater.inflate(R.layout.fragment_schedules_devices, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView = view.findViewById(R.id.schedules_devices_list);
        mParentViewModel.getUnattachedDeviceList().observe(getViewLifecycleOwner(),
                unattachedDevices -> {
                    List<UnattachedDeviceItemModel> scheduleListItemModels = new ArrayList<>();
                    for (DeviceIdNameUiModel uiModel : unattachedDevices) {
                        scheduleListItemModels.add(new UnattachedDeviceItemModel(uiModel));
                    }
                    mAdapter = new ArrayAdapter<>(requireContext(),
                            R.layout.view_custom_list_item_multiple_choise,
                            scheduleListItemModels);
                    mListView.setAdapter(mAdapter);
                });

        this.addMenuProvider(view);
    }

    private void addMenuProvider(@NonNull final View view) {
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.list_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.list_done_button) {
                    final List<String> deviceIdListForAttach = new ArrayList<>();
                    for (int i = 0; i < mListView.getCount(); i++) {
                        if (mListView.isItemChecked(i)) {
                            deviceIdListForAttach.add(
                                    ((UnattachedDeviceItemModel)
                                            mListView.getItemAtPosition(i)).getId());
                        }
                    }
                    mParentViewModel.attachMultipleDevicesToSchedule(deviceIdListForAttach);
                    Navigation.findNavController(view).navigateUp();
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private static class UnattachedDeviceItemModel extends DeviceIdNameUiModel {
        public UnattachedDeviceItemModel(final DeviceIdNameUiModel model) {
            super(model.getId(), model.getName());
        }

        @NonNull
        @Override
        public String toString() {
            return super.getName();
        }
    }
}