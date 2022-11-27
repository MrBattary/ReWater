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

import michael.linker.rewater.R;
import michael.linker.rewater.ui.advanced.home.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {
    private TextView mUsernameTextView;

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
    }

    private void initFieldsData() {
        mViewModel.getProfileModel().observe(getViewLifecycleOwner(),
                model -> mUsernameTextView.setText(model.getUsername()));
        mViewModel.requireProfileUpdate();
    }
}