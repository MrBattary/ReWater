package michael.linker.rewater.ui.advanced.networks.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import michael.linker.rewater.R;
import michael.linker.rewater.data.res.IntegersProvider;
import michael.linker.rewater.databinding.FragmentNetworksBinding;
import michael.linker.rewater.ui.advanced.networks.adapter.NetworksItemAdapter;
import michael.linker.rewater.ui.advanced.networks.viewmodel.NetworksViewModel;
import michael.linker.rewater.ui.animation.transition.OrderedTransition;

public class NetworksFragment extends Fragment {
    private NetworksViewModel mViewModel;
    private FragmentNetworksBinding mBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(NetworksViewModel.class);
        mBinding = FragmentNetworksBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        OrderedTransition transition = new OrderedTransition();
        transition.setDuration(
                IntegersProvider.getInteger(R.integer.networks_fragment_animation_time));

        RecyclerView recyclerView = view.findViewById(R.id.networks_recycler_view);
        transition.setRootView(recyclerView);
        transition.addChangeBoundsTarget(view.findViewById(R.id.networks_recycler_view));
        transition.addChangeBoundsTarget(
                view.findViewById(R.id.networks_recycler_view_wrapper));


        mViewModel.getNetworkList().observe(getViewLifecycleOwner(), networkList -> {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(
                    new NetworksItemAdapter(getContext(), networkList, transition));
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}