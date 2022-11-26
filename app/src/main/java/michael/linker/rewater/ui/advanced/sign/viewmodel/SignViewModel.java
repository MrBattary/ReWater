package michael.linker.rewater.ui.advanced.sign.viewmodel;

import androidx.lifecycle.ViewModel;

import michael.linker.rewater.config.RepositoryConfiguration;
import michael.linker.rewater.data.repository.user.IUsersRepository;
import michael.linker.rewater.data.repository.user.UsersRepositoryAccessDeniedException;
import michael.linker.rewater.data.repository.user.UsersRepositoryAlreadyExistsException;
import michael.linker.rewater.data.repository.user.UsersRepositoryNotFoundException;
import michael.linker.rewater.data.repository.user.model.SignInUserRepositoryModel;
import michael.linker.rewater.data.repository.user.model.SignUpUserRepositoryModel;

public class SignViewModel extends ViewModel {
    private final IUsersRepository mUsersRepository;

    private String mUsername, mEmail, mPassword;

    public SignViewModel() {
        mUsersRepository = RepositoryConfiguration.getUsersRepository();
    }


    public void setUsername(final String username) {
        mUsername = username;
    }

    public void setEmail(final String email) {
        mEmail = email;
    }

    public void setPassword(final String password) {
        mPassword = password;
    }

    public void commitAndSignUp() throws SignViewModelFailedException {
        try {
            mUsersRepository.signUp(
                    new SignUpUserRepositoryModel(
                            mUsername,
                            mPassword,
                            mEmail));
        } catch (UsersRepositoryAlreadyExistsException e) {
            throw new SignViewModelFailedException(e);
        }
    }

    public void commitAndSignIn() throws SignViewModelFailedException {
        try {
            mUsersRepository.signIn(
                    new SignInUserRepositoryModel(mUsername, mPassword));
        } catch (UsersRepositoryAccessDeniedException e) {
            throw new SignViewModelFailedException(e);
        }
    }

    public void autoSignIn() throws SignViewModelFailedException {
        try {
            mUsersRepository.refreshSessionToken();
        } catch (UsersRepositoryNotFoundException | UsersRepositoryAccessDeniedException e) {
            throw new SignViewModelFailedException(e);
        }
    }
}