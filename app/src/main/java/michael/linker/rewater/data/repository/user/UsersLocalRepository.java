package michael.linker.rewater.data.repository.user;

import michael.linker.rewater.R;
import michael.linker.rewater.config.StubDataConfiguration;
import michael.linker.rewater.data.local.db.user.IUserData;
import michael.linker.rewater.data.local.db.user.UserData;
import michael.linker.rewater.data.local.db.user.UserDataNotFoundException;
import michael.linker.rewater.data.local.db.user.model.UserAuthTokenDataModel;
import michael.linker.rewater.data.local.db.user.model.UserDataModel;
import michael.linker.rewater.data.local.db.user.model.UserSessionTokenDataModel;
import michael.linker.rewater.data.local.db.user.model.UserTokensDataModel;
import michael.linker.rewater.data.local.stub.IUsersData;
import michael.linker.rewater.data.local.stub.UsersDataException;
import michael.linker.rewater.data.local.stub.model.AuthTokenModel;
import michael.linker.rewater.data.local.stub.model.FullUserModel;
import michael.linker.rewater.data.local.stub.model.SessionTokenModel;
import michael.linker.rewater.data.local.stub.model.SignInUserModel;
import michael.linker.rewater.data.repository.user.model.SignInUserRepositoryModel;
import michael.linker.rewater.data.repository.user.model.SignUpUserRepositoryModel;
import michael.linker.rewater.data.repository.user.model.UserRepositoryModel;
import michael.linker.rewater.data.res.StringsProvider;

public class UsersLocalRepository implements IUsersRepository {
    private final IUsersData mUsersData;
    private final IUserData mUserData;

    public UsersLocalRepository() {
        mUsersData = StubDataConfiguration.getUsersData();
        mUserData = new UserData();
    }

    @Override
    public void signIn(SignInUserRepositoryModel model)
            throws UsersRepositoryAccessDeniedException {
        try {
            final AuthTokenModel authToken = mUsersData.signIn(new SignInUserModel(
                    model.getUsername(),
                    model.getPassword()));
            final SessionTokenModel sessionToken = this.getNewSessionTokenByAuthToken(authToken);

            mUserData.addUser(new UserDataModel(
                    model.getUsername(),
                    new UserTokensDataModel(
                            new UserAuthTokenDataModel(
                                    authToken.getToken()
                            ),
                            new UserSessionTokenDataModel(
                                    sessionToken.getToken(),
                                    sessionToken.getValidUntil()
                            ))
            ));
            mUserData.activateUser(model.getUsername());
        } catch (UsersDataException e) {
            throw new UsersRepositoryAccessDeniedException();
        }
    }

    @Override
    public void signUp(SignUpUserRepositoryModel model)
            throws UsersRepositoryAlreadyExistsException {
        try {
            mUsersData.signUp(new FullUserModel(
                    model.getUsername(),
                    model.getPassword(),
                    model.getEmail()
            ));
        } catch (UsersDataException e) {
            throw new UsersRepositoryAlreadyExistsException(e.getMessage());
        }
    }

    @Override
    public void signOut() {
        final UserDataModel userDataModel = mUserData.getActiveUserModel();
        if (userDataModel != null) {
            mUserData.removeUser(userDataModel.getUsername());
        }
    }

    @Override
    public void refreshSessionToken()
            throws UsersRepositoryNotFoundException, UsersRepositoryAccessDeniedException {
        try {
            final UserAuthTokenDataModel internalAuthToken = mUserData.getAuthTokenOfActiveUser();
            final SessionTokenModel token = this.getNewSessionTokenByAuthToken(new AuthTokenModel(
                    internalAuthToken.getToken()
            ));
            mUserData.updateSessionTokenForActiveUser(
                    new UserSessionTokenDataModel(token.getToken(), token.getValidUntil()));
        } catch (UserDataNotFoundException e) {
            throw new UsersRepositoryNotFoundException(e.getMessage());
        } catch (UsersDataException e) {
            throw new UsersRepositoryAccessDeniedException(
                    StringsProvider.getString(R.string.sign_failure_auth_token_not_accepted));
        }
    }

    @Override
    public UserRepositoryModel getActiveUserData() throws UsersRepositoryNotFoundException {
        try {
            final UserDataModel userDataModel = mUserData.getActiveUserModel();
            return new UserRepositoryModel(userDataModel.getUsername());
        } catch (UserDataNotFoundException e) {
            throw new UsersRepositoryNotFoundException(e.getMessage());
        }
    }

    private SessionTokenModel getNewSessionTokenByAuthToken(AuthTokenModel tokenModel)
            throws UsersDataException {
        return mUsersData.refreshToken(tokenModel);
    }
}
