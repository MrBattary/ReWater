package michael.linker.rewater.ui.advanced.devices.view;

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
import michael.linker.rewater.ui.advanced.devices.viewmodel.DevicesViewModel;
import michael.linker.rewater.ui.advanced.devices.viewmodel.DevicesViewModelFailedException;
import michael.linker.rewater.ui.advanced.schedules.model.ScheduleUiModel;
import michael.linker.rewater.ui.elementary.toast.ToastProvider;

public class ScheduleListDeviceFragment extends Fragment {
    private ListView mListView;
    private ViewGroup mSchedulesNotFoundView;
    private ArrayAdapter<ScheduleListItemModel> mAdapter;
    private DevicesViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        NavController navController = NavHostFragment.findNavController(this);
        ViewModelStoreOwner viewModelStoreOwner = navController.getViewModelStoreOwner(
                R.id.root_navigation_devices);
        mViewModel = new ViewModelProvider(viewModelStoreOwner).get(DevicesViewModel.class);
        return inflater.inflate(R.layout.fragment_devices_schedules, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView = view.findViewById(R.id.devices_schedules_list);
        mSchedulesNotFoundView = view.findViewById(R.id.devices_schedules_not_found);

        mViewModel.getSchedulesModels().observe(getViewLifecycleOwner(),
                compactScheduleModels -> {
                    if (compactScheduleModels.size() > 0) {
                        List<ScheduleListItemModel> scheduleListItemModels = new ArrayList<>();
                        for (ScheduleUiModel model : compactScheduleModels) {
                            scheduleListItemModels.add(new ScheduleListItemModel(model));
                        }
                        mAdapter = new ArrayAdapter<>(requireContext(),
                                R.layout.view_custom_list_item_multiple_choise,
                                scheduleListItemModels);
                        mListView.setAdapter(mAdapter);

                        mListView.setVisibility(View.VISIBLE);
                        mSchedulesNotFoundView.setVisibility(View.GONE);
                    } else {
                        mListView.setVisibility(View.GONE);
                        mSchedulesNotFoundView.setVisibility(View.VISIBLE);
                    }
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
                    for (int i = 0; i < mListView.getCount(); i++) {
                        if (mListView.isItemChecked(i)) {
                            final ScheduleListItemModel chosenSchedule =
                                    (ScheduleListItemModel) mListView.getItemAtPosition(i);
                            try {
                                mViewModel.attachParentsByScheduleId(
                                        chosenSchedule.getScheduleId(),
                                        chosenSchedule.getNetworkId());
                                Navigation.findNavController(view).navigateUp();
                            } catch (DevicesViewModelFailedException e) {
                                ToastProvider.showShort(requireContext(), e.getMessage());
                            }
                            return true;
                        }
                    }
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private static class ScheduleListItemModel {
        private final String mScheduleId, mScheduleName, mNetworkId;

        public ScheduleListItemModel(final ScheduleUiModel model) {
            mScheduleId = model.getId();
            mScheduleName = model.getName();
            mNetworkId = model.getParentNetworkId();
        }

        @NonNull
        @Override
        public String toString() {
            return mScheduleName;
        }

        public String getScheduleId() {
            return mScheduleId;
        }

        public String getNetworkId() {
            return mNetworkId;
        }
    }
}