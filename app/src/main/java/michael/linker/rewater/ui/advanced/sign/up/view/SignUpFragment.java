package michael.linker.rewater.ui.advanced.sign.up.view;

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

import com.google.android.material.button.MaterialButton;

import michael.linker.rewater.R;
import michael.linker.rewater.ui.advanced.sign.up.viewmodel.SignUpViewModel;

public class SignUpFragment extends Fragment {
    private MaterialButton mSignInSwitchButton, mSignUpButton;

    private SignUpViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        NavController navController = NavHostFragment.findNavController(this);
        ViewModelStoreOwner viewModelStoreOwner = navController.getViewModelStoreOwner(
                R.id.navigation_sign);
        mViewModel = new ViewModelProvider(viewModelStoreOwner).get(SignUpViewModel.class);
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.initFields(view);
        this.initButtons(view);
    }

    private void initFields(final View view) {
        mSignInSwitchButton = view.findViewById(R.id.sign_up_sign_in_button);
        mSignUpButton = view.findViewById(R.id.sign_up_sign_up_button);
    }

    private void initButtons(final View view) {
        final NavController navController = Navigation.findNavController(view);

        mSignInSwitchButton.setOnClickListener(
                l -> navController.navigate(R.id.navigation_action_sign_up_to_sign_in));
    }

}