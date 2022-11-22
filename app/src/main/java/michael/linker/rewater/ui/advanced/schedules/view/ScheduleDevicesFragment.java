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
import androidx.navigation.fragment.NavHostFragment;

import michael.linker.rewater.R;
import michael.linker.rewater.ui.advanced.schedules.viewmodel.UpdateScheduleViewModel;

public class ScheduleDevicesFragment extends Fragment {

    private UpdateScheduleViewModel mParentViewModel;

    public static ScheduleDevicesFragment newInstance() {
        return new ScheduleDevicesFragment();
    }

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
    }

}