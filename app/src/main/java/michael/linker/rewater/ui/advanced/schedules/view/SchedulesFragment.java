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
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import michael.linker.rewater.R;
import michael.linker.rewater.data.res.IntegersProvider;
import michael.linker.rewater.ui.advanced.networks.viewmodel.NetworksDevicesLinkViewModel;
import michael.linker.rewater.ui.advanced.schedules.adapter.SchedulesItemAdapter;
import michael.linker.rewater.ui.advanced.schedules.viewmodel.SchedulesViewModel;
import michael.linker.rewater.ui.advanced.schedules.viewmodel.UpdateScheduleViewModel;
import michael.linker.rewater.ui.animation.transition.OrderedTransition;

public class SchedulesFragment extends Fragment {
    private NetworksDevicesLinkViewModel mLinkViewModel;
    private SchedulesViewModel mViewModel;
    private UpdateScheduleViewModel mChildViewModel;

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
        mChildViewModel = new ViewModelProvider(currentViewModelStoreOwner).get(
                UpdateScheduleViewModel.class);
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
            initAddFloatingActionButton(view, idNameModel.getId());
        });

        OrderedTransition transition = new OrderedTransition();
        transition.setDuration(
                IntegersProvider.getInteger(R.integer.transition_animation_time));

        RecyclerView recyclerView = view.findViewById(R.id.schedules_recycler_view);
        transition.setRootView(recyclerView);
        transition.addChangeBoundsTarget(view.findViewById(R.id.schedules));
        transition.addChangeBoundsTarget(view.findViewById(R.id.schedules_recycler_view));

        ViewGroup mSchedulesNotFound = view.findViewById(R.id.schedules_not_found);

        mViewModel.getScheduleList().observe(getViewLifecycleOwner(), list -> {
            if (list.size() > 0) {
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(
                        new SchedulesItemAdapter(
                                getContext(),
                                mViewModel,
                                mChildViewModel,
                                list,
                                transition));
                recyclerView.setVisibility(View.VISIBLE);
                mSchedulesNotFound.setVisibility(View.GONE);
            } else {
                recyclerView.setVisibility(View.GONE);
                mSchedulesNotFound.setVisibility(View.VISIBLE);
            }
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

    private void initAddFloatingActionButton(@NotNull final View view, final String id) {
        final FloatingActionButton addFab = view.findViewById(R.id.schedules_add_fab);
        addFab.setOnClickListener(buttonView -> {
                    mChildViewModel.setParentNetworkIdAndRefreshViewModel(id);
                    Navigation.findNavController(view).navigate(
                            R.id.navigation_action_schedules_to_schedules_add);
                }
        );
    }
}