package michael.linker.rewater.ui.advanced.networks.view;

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
import michael.linker.rewater.ui.advanced.networks.adapter.NetworksItemAdapter;
import michael.linker.rewater.ui.advanced.networks.viewmodel.NetworksDevicesLinkViewModel;
import michael.linker.rewater.ui.advanced.networks.viewmodel.NetworksViewModel;
import michael.linker.rewater.ui.animation.transition.OrderedTransition;

public class NetworksFragment extends Fragment {
    private NetworksViewModel mViewModel;
    private NetworksDevicesLinkViewModel mLinkViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        final NavController navController = NavHostFragment.findNavController(this);

        final ViewModelStoreOwner viewModelStoreOwner = navController.getViewModelStoreOwner(
                R.id.root_navigation_networks);
        final ViewModelProvider viewModelRootProvider = new ViewModelProvider(viewModelStoreOwner);

        mViewModel = viewModelRootProvider.get(NetworksViewModel.class);
        mLinkViewModel = viewModelRootProvider.get(NetworksDevicesLinkViewModel.class);

        return inflater.inflate(R.layout.fragment_networks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.updateListsFromRepositories();

        OrderedTransition transition = new OrderedTransition();
        transition.setDuration(
                IntegersProvider.getInteger(R.integer.transition_animation_time));

        RecyclerView recyclerView = view.findViewById(R.id.networks_recycler_view);
        transition.setRootView(recyclerView);
        transition.addChangeBoundsTarget(view.findViewById(R.id.networks));
        transition.addChangeBoundsTarget(view.findViewById(R.id.networks_recycler_view));

        ViewGroup mNetworksNotFound = view.findViewById(R.id.networks_not_found);

        mViewModel.getCompactNetworkModels().observe(getViewLifecycleOwner(), list -> {
            if(list.size() > 0) {
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(
                        new NetworksItemAdapter(getContext(), mViewModel, mLinkViewModel, list, transition));
                recyclerView.setVisibility(View.VISIBLE);
                mNetworksNotFound.setVisibility(View.GONE);
            } else {
                recyclerView.setVisibility(View.GONE);
                mNetworksNotFound.setVisibility(View.VISIBLE);
            }
        });

        initAddFloatingActionButton(view);
    }

    private void initAddFloatingActionButton(@NotNull View view) {
        final FloatingActionButton addFab = view.findViewById(R.id.networks_add_fab);
        addFab.setOnClickListener(buttonView ->
                Navigation.findNavController(view).navigate(
                        R.id.navigation_action_networks_to_networks_add)
        );
    }
}