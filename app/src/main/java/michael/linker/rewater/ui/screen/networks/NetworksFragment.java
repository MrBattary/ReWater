package michael.linker.rewater.ui.networks;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import michael.linker.rewater.R;
import michael.linker.rewater.adapter.NetworkItemAdapter;
import michael.linker.rewater.application.App;
import michael.linker.rewater.data.NetworksData;

public class NetworksFragment extends Fragment {

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

        final NetworksData data = new NetworksData();

        Activity activity = getActivity();
        RecyclerView recyclerView = activity.findViewById(R.id.networks_recycler_view);
        recyclerView.setAdapter(new NetworkItemAdapter(getContext(), data.getNetworksList()));
    }
}