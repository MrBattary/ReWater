package michael.linker.rewater.ui.advanced.sign.in.view;

import android.app.Activity;
import android.content.Intent;
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
import michael.linker.rewater.activity.MainActivity;
import michael.linker.rewater.ui.advanced.sign.in.viewmodel.SignInViewModel;
import michael.linker.rewater.ui.advanced.sign.in.viewmodel.SignInViewModelFailedException;
import michael.linker.rewater.ui.elementary.input.InputNotAllowedException;
import michael.linker.rewater.ui.elementary.input.text.FocusableTextInputView;
import michael.linker.rewater.ui.elementary.input.text.IFocusableTextInputView;
import michael.linker.rewater.ui.elementary.input.text.IPasswordTextInputView;
import michael.linker.rewater.ui.elementary.input.text.PasswordTextInputView;
import michael.linker.rewater.ui.elementary.toast.ToastProvider;

public class SignInFragment extends Fragment {
    private MaterialButton mSignInButton, mSignUpSwitchButton, mRestorePasswordButton;
    private IFocusableTextInputView mUsernameInputView;
    private IPasswordTextInputView mPasswordInputView;

    private SignInViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        NavController navController = NavHostFragment.findNavController(this);
        ViewModelStoreOwner viewModelStoreOwner = navController.getViewModelStoreOwner(
                R.id.navigation_sign);
        mViewModel = new ViewModelProvider(viewModelStoreOwner).get(SignInViewModel.class);

        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.initFields(view);
        this.initInputLogics();
        this.initButtons(view);
    }

    private void initFields(final View view) {
        mUsernameInputView = new FocusableTextInputView(
                view.findViewById(R.id.sign_in_input_username),
                view.findViewById(R.id.sign_in_input_username_input));
        mPasswordInputView = new PasswordTextInputView(
                view.findViewById(R.id.sign_in_input_password),
                view.findViewById(R.id.sign_in_input_password_input));
        mSignInButton = view.findViewById(R.id.sign_in_sign_in_button);
        mSignUpSwitchButton = view.findViewById(R.id.sign_in_sign_up_button);
        mRestorePasswordButton = view.findViewById(R.id.sign_in_restore_password_text_button);
    }

    private void initInputLogics() {
        mUsernameInputView.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                this.saveUsername();
            }
        });
        mPasswordInputView.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                this.savePassword();
            }
        });
    }

    private void initButtons(final View view) {
        final NavController navController = Navigation.findNavController(view);

        mSignUpSwitchButton.setOnClickListener(
                l -> navController.navigate(R.id.navigation_action_sign_in_to_sign_up));
        mSignInButton.setOnClickListener(l -> {
            try {
                this.saveData();
                mViewModel.commitAndSignIn();
                this.moveToMainActivity();
            } catch (SignInViewModelFailedException e) {
                ToastProvider.showShort(requireContext(), e.getMessage());
            }
        });
        mRestorePasswordButton.setOnClickListener(l -> {
            // TODO (ML): Move to restore password fragment
        });
    }
    private void moveToMainActivity() {
        final Activity activity = requireActivity();
        final Intent intent = new Intent(activity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        activity.finish();
    }

    private void saveData() {
        this.saveUsername();
        this.savePassword();
    }

    private void saveUsername() {
        try {
            mViewModel.setUsername(mUsernameInputView.getText());
        } catch (InputNotAllowedException ignored) {
        }
    }

    private void savePassword() {
        try {
            mViewModel.setPassword(mPasswordInputView.getPasswordHash());
        } catch (InputNotAllowedException ignored) {
        }
    }
}