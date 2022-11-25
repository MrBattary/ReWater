package michael.linker.rewater.ui.advanced.sign.in.viewmodel;

import androidx.lifecycle.ViewModel;

import michael.linker.rewater.config.RepositoryConfiguration;
import michael.linker.rewater.data.repository.user.IUsersRepository;
import michael.linker.rewater.data.repository.user.UsersRepositoryAccessDeniedException;
import michael.linker.rewater.data.repository.user.model.SignInUserRepositoryModel;

public class SignInViewModel extends ViewModel {
    private final IUsersRepository mUsersRepository;

    private String mUsername, mPassword;

    public SignInViewModel() {
        mUsersRepository = RepositoryConfiguration.getUsersRepository();
    }

    public void setUsername(final String username) {
        mUsername = username;
    }

    public void setPassword(final String password) {
        mPassword = password;
    }

    public void commitAndSignIn() throws SignInViewModelFailedException {
        try {
            mUsersRepository.signIn(new SignInUserRepositoryModel(mUsername, mPassword));
        } catch (UsersRepositoryAccessDeniedException e) {
            throw new SignInViewModelFailedException(e.getMessage());
        }
    }
}