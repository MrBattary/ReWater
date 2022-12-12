package michael.linker.rewater.ui.advanced.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import michael.linker.rewater.data.web.api.common.request.PageSizeCommonRequest;
import michael.linker.rewater.ui.advanced.home.adapter.HomeHistoryItemAdapter;
import michael.linker.rewater.ui.advanced.home.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {
    private static final PageSizeCommonRequest
            HOME_HISTORY_PAGINATION_REQUEST = new PageSizeCommonRequest(1, 5);
    private TextView mUsernameTextView;
    private RecyclerView mHistoryEventsRecyclerView;
    private HomeViewModel mViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        final NavController navController = NavHostFragment.findNavController(this);
        final ViewModelStoreOwner currentViewModelStoreOwner = navController
                .getViewModelStoreOwner(R.id.root_navigation_home);
        mViewModel = new ViewModelProvider(currentViewModelStoreOwner).get(HomeViewModel.class);

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.initFields(view);
        this.initFieldsData();
    }

    private void initFields(final View view) {
        mUsernameTextView = view.findViewById(R.id.home_greeting_username);
        mHistoryEventsRecyclerView = view.findViewById(R.id.home_history_events);
    }

    private void initFieldsData() {
        mViewModel.getProfileModel().observe(getViewLifecycleOwner(),
                model -> mUsernameTextView.setText(model.getUsername()));
        mViewModel.getHistoryList().observe(getViewLifecycleOwner(), historyModels -> {
            mHistoryEventsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            mHistoryEventsRecyclerView.swapAdapter(
                    new HomeHistoryItemAdapter(requireContext(), historyModels), false);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.requireProfileUpdate();
        mViewModel.requireHistoryUpdate(HOME_HISTORY_PAGINATION_REQUEST);
    }
}