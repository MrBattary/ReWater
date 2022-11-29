package michael.linker.rewater.ui.advanced.sign.view;

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
import michael.linker.rewater.data.res.IntegersProvider;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.ui.advanced.sign.model.SignBundle;
import michael.linker.rewater.ui.advanced.sign.model.SignUiModel;
import michael.linker.rewater.ui.advanced.sign.viewmodel.SignViewModel;
import michael.linker.rewater.ui.advanced.sign.viewmodel.SignViewModelFailedException;
import michael.linker.rewater.ui.elementary.input.InputNotAllowedException;
import michael.linker.rewater.ui.elementary.input.text.FocusableTextInputView;
import michael.linker.rewater.ui.elementary.input.text.IFocusableTextInputView;
import michael.linker.rewater.ui.elementary.input.text.IPasswordTextInputView;
import michael.linker.rewater.ui.elementary.input.text.PasswordTextInputView;
import michael.linker.rewater.ui.elementary.toast.ToastProvider;

public class SignUpFragment extends Fragment {
    private MaterialButton mSignInSwitchButton, mSignUpButton;
    private IFocusableTextInputView mUsernameInputView, mEmailInputView;
    private IPasswordTextInputView mPasswordInputView;

    private SignUiModel mSignUiModel;
    private SignViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        NavController navController = NavHostFragment.findNavController(this);
        ViewModelStoreOwner viewModelStoreOwner = navController.getViewModelStoreOwner(
                R.id.navigation_sign);
        mViewModel = new ViewModelProvider(viewModelStoreOwner).get(SignViewModel.class);

        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.unpackBundle();
        this.initFields(view);
        this.initInputLogics();
        this.initButtons(view);
    }

    private void unpackBundle() {
        final Bundle bundle = getArguments();
        if (bundle != null) {
            mSignUiModel = SignBundle.INSTANCE.unpack(bundle);
        } else {
            mSignUiModel = new SignUiModel(null);
        }
    }

    private void initFields(final View view) {
        mUsernameInputView = new FocusableTextInputView(
                view.findViewById(R.id.sign_up_input_username),
                view.findViewById(R.id.sign_up_input_username_input));
        mEmailInputView = new FocusableTextInputView(
                view.findViewById(R.id.sign_up_input_email),
                view.findViewById(R.id.sign_up_input_email_input));
        mPasswordInputView = new PasswordTextInputView(
                view.findViewById(R.id.sign_up_input_password),
                view.findViewById(R.id.sign_up_input_password_input));
        mSignInSwitchButton = view.findViewById(R.id.sign_up_sign_in_button);
        mSignUpButton = view.findViewById(R.id.sign_up_sign_up_button);
    }

    private void initInputLogics() {
        mUsernameInputView.setMinLimit(
                IntegersProvider.getInteger(R.integer.input_min_limit_header),
                StringsProvider.getString(R.string.input_error_username_lack));
        mUsernameInputView.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                try {
                    this.saveUsername();
                } catch (InputNotAllowedException ignored) {
                }
            }
        });
        mEmailInputView.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                try {
                    this.saveEmail();
                } catch (InputNotAllowedException ignored) {
                }
            }
        });
        mPasswordInputView.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                try {
                    this.savePassword();
                } catch (InputNotAllowedException ignored) {
                }
            }
        });
    }

    private void initButtons(final View view) {

        mSignInSwitchButton.setOnClickListener(l -> this.moveToSignIn(view));

        mSignUpButton.setOnClickListener(l -> {
            try {
                this.saveData();
                mViewModel.commitAndSignUp();
                mSignUiModel.setUsername(mUsernameInputView.getText());
                this.moveToSignIn(view);
            } catch (SignViewModelFailedException e) {
                ToastProvider.showShort(requireContext(), e.getMessage());
            } catch (InputNotAllowedException ignored) {

            }
        });
    }

    private void moveToSignIn(final View view) {
        Navigation.findNavController(view).navigate(R.id.navigation_action_sign_up_to_sign_in,
                SignBundle.INSTANCE.pack(mSignUiModel));
    }

    private void saveData() throws InputNotAllowedException {
        this.saveUsername();
        this.saveEmail();
        this.savePassword();
    }

    private void saveUsername() throws InputNotAllowedException {
        mViewModel.setUsername(mUsernameInputView.getText());
    }

    private void saveEmail() throws InputNotAllowedException {
        mViewModel.setEmail(mEmailInputView.getText());
    }

    private void savePassword() throws InputNotAllowedException {
        mViewModel.setPassword(mPasswordInputView.validateAndGetPasswordHash());
    }
}