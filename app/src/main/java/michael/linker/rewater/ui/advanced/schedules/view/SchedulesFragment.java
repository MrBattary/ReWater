package michael.linker.rewater.ui.advanced.schedules.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import michael.linker.rewater.R;
import michael.linker.rewater.ui.advanced.networks.viewmodel.NetworksDevicesLinkViewModel;
import michael.linker.rewater.ui.advanced.schedules.viewmodel.SchedulesViewModel;

public class SchedulesFragment extends Fragment {

    private NetworksDevicesLinkViewModel mLinkViewModel;
    private SchedulesViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        final NavController navController = NavHostFragment.findNavController(this);
        final ViewModelStoreOwner currentViewModelStoreOwner = navController
                .getViewModelStoreOwner(R.id.root_navigation_schedules);
        final ViewModelStoreOwner linkViewModelStoreOwner = navController
                .getViewModelStoreOwner(R.id.root_navigation_networks);

        mViewModel = new ViewModelProvider(currentViewModelStoreOwner).get(
                SchedulesViewModel.class);
        mLinkViewModel = new ViewModelProvider(linkViewModelStoreOwner).get(
                NetworksDevicesLinkViewModel.class);

        return inflater.inflate(R.layout.fragment_schedules, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLinkViewModel.getParentNetworkIdName().observe(getViewLifecycleOwner(), idNameModel -> {
            initSupportActionBar(idNameModel.getName());
            mViewModel.setParentNetworkIdAndLoadSchedules(idNameModel.getId());
        });
    }

    private void initSupportActionBar(final String parentNetworkName) {
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            final ActionBar supportActionBar = activity.getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setTitle(parentNetworkName);
            }
        }
    }
}