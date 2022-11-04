package michael.linker.rewater.ui.screen.networks;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import michael.linker.rewater.R;
import michael.linker.rewater.config.DataConfiguration;
import michael.linker.rewater.data.networks.NetworksLocalData;
import michael.linker.rewater.ui.animation.transition.OrderedTransition;
import michael.linker.rewater.ui.view.adapter.NetworksItemAdapter;

public class NetworksFragment extends Fragment {
    private DataConfiguration dataConfiguration;
    private NetworksViewModel mViewModel;

    public static NetworksFragment newInstance() {
        return new NetworksFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_networks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final NetworksLocalData networksLocalData = dataConfiguration.getNetworksData();

        Activity activity = getActivity();
        OrderedTransition transition = new OrderedTransition();
        transition.setDuration(150);

        RecyclerView recyclerView = activity.findViewById(R.id.networks_recycler_view);
        transition.setRootView(recyclerView);
        transition.addChangeBoundsTarget(activity.findViewById(R.id.networks_recycler_view));
        transition.addChangeBoundsTarget(
                activity.findViewById(R.id.networks_recycler_view_wrapper));


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(
                new NetworksItemAdapter(getContext(), networksLocalData.getNetworks(),
                        transition));

        final FloatingActionButton addFab = activity.findViewById(R.id.networks_add_fab);
        addFab.setOnClickListener(buttonView -> {
            Navigation.findNavController(view).navigate(R.id.navigation_action_networks_to_networks_add);
        });
    }
}