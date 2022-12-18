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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import michael.linker.rewater.R;
import michael.linker.rewater.ui.advanced.devices.adapter.DevicesHistoryItemAdapter;
import michael.linker.rewater.ui.advanced.devices.viewmodel.DevicesViewModel;

public class DevicesHistoryFragment extends Fragment {
    private ViewGroup mHistoryEventsNotFoundPlaceholder;
    private RecyclerView mHistoryEventsRecyclerView;

    private DevicesViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        NavController navController = NavHostFragment.findNavController(this);
        ViewModelStoreOwner viewModelStoreOwner = navController.getViewModelStoreOwner(
                R.id.root_navigation_devices);
        mViewModel = new ViewModelProvider(viewModelStoreOwner).get(DevicesViewModel.class);
        return inflater.inflate(R.layout.fragment_history_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.initFields(view);
        this.initFieldsData();
    }

    private void initFields(final View view) {
        mHistoryEventsNotFoundPlaceholder = view.findViewById(R.id.history_list_events_not_found);
        mHistoryEventsRecyclerView = view.findViewById(R.id.history_list_events);
    }

    private void initFieldsData() {
        mViewModel.getDeviceHistoryUiModels().observe(getViewLifecycleOwner(), historyModels -> {
            if (historyModels.size() > 0) {
                mHistoryEventsRecyclerView.setLayoutManager(
                        new LinearLayoutManager(requireContext()));
                mHistoryEventsRecyclerView.swapAdapter(
                        new DevicesHistoryItemAdapter(requireContext(), historyModels), false);
                mHistoryEventsRecyclerView.setVisibility(View.VISIBLE);
                mHistoryEventsNotFoundPlaceholder.setVisibility(View.GONE);
            } else {
                mHistoryEventsRecyclerView.setVisibility(View.GONE);
                mHistoryEventsNotFoundPlaceholder.setVisibility(View.VISIBLE);
            }
        });
    }
}