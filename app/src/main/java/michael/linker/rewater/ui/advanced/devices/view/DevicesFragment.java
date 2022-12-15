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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import michael.linker.rewater.R;
import michael.linker.rewater.data.res.IntegersProvider;
import michael.linker.rewater.ui.advanced.devices.adapter.DevicesItemAdapter;
import michael.linker.rewater.ui.advanced.devices.viewmodel.DevicesViewModel;
import michael.linker.rewater.ui.animation.transition.OrderedTransition;

public class DevicesFragment extends Fragment {
    private DevicesViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        NavController navController = NavHostFragment.findNavController(this);
        ViewModelStoreOwner viewModelStoreOwner = navController.getViewModelStoreOwner(
                R.id.root_navigation_devices);
        mViewModel = new ViewModelProvider(viewModelStoreOwner).get(DevicesViewModel.class);

        return inflater.inflate(R.layout.fragment_devices, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.updateListsFromRepositories();

        OrderedTransition transition = new OrderedTransition();
        transition.setDuration(
                IntegersProvider.getInteger(R.integer.transition_animation_time));

        RecyclerView recyclerView = view.findViewById(R.id.devices_recycler_view);
        transition.setRootView(recyclerView);
        transition.addChangeBoundsTarget(view.findViewById(R.id.devices));
        transition.addChangeBoundsTarget(view.findViewById(R.id.devices_recycler_view));

        ViewGroup mDevicesNotFound = view.findViewById(R.id.devices_not_found);

        mViewModel.getDeviceCardModels().observe(getViewLifecycleOwner(), list -> {
            if (list.size() > 0) {
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(
                        new DevicesItemAdapter(getContext(), mViewModel, list, transition));
                recyclerView.setVisibility(View.VISIBLE);
                mDevicesNotFound.setVisibility(View.GONE);
            } else {
                recyclerView.setVisibility(View.GONE);
                mDevicesNotFound.setVisibility(View.VISIBLE);
            }
        });

        initAddFloatingActionButton(view);
    }

    private void initAddFloatingActionButton(@NotNull View view) {
        final FloatingActionButton addFab = view.findViewById(R.id.devices_add_fab);
        addFab.setOnClickListener(buttonView ->
                Navigation.findNavController(view).navigate(
                        R.id.navigation_action_devices_to_devices_add)
        );
    }

}