package michael.linker.rewater.ui.advanced.profile.view;

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

import com.google.android.material.button.MaterialButton;

import michael.linker.rewater.R;
import michael.linker.rewater.activity.ActivityGate;
import michael.linker.rewater.ui.advanced.profile.viewmodel.ProfileViewModel;

public class ProfileFragment extends Fragment {
    private TextView mUsernameTextView;
    private MaterialButton mSignOutButton;

    private ProfileViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        final NavController navController = NavHostFragment.findNavController(this);
        final ViewModelStoreOwner currentViewModelStoreOwner = navController
                .getViewModelStoreOwner(R.id.root_navigation_profile);
        mViewModel = new ViewModelProvider(currentViewModelStoreOwner).get(ProfileViewModel.class);

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.initFields(view);
        this.initFieldsData();
        this.initButtons();
    }

    private void initFields(final View view) {
        mUsernameTextView = view.findViewById(R.id.profile_user_username);
        mSignOutButton = view.findViewById(R.id.profile_sign_out_button);
    }

    private void initFieldsData() {
        mViewModel.getProfileModel().observe(getViewLifecycleOwner(),
                model -> mUsernameTextView.setText(model.getUsername()));
        mViewModel.requireProfileUpdate();
    }

    private void initButtons() {
        mSignOutButton.setOnClickListener(l -> {
            mViewModel.signOut();
            ActivityGate.moveToSignActivityOnSignOut(requireActivity());
        });
    }

}