package michael.linker.rewater.data.local.db.user;

import java.time.Instant;

import michael.linker.rewater.config.DatabaseConfiguration;
import michael.linker.rewater.data.local.db.AppDatabase;
import michael.linker.rewater.data.local.db.user.entity.AuthToken;
import michael.linker.rewater.data.local.db.user.entity.SessionToken;
import michael.linker.rewater.data.local.db.user.entity.User;
import michael.linker.rewater.data.local.db.user.model.UserAuthTokenDataModel;
import michael.linker.rewater.data.local.db.user.model.UserDataModel;
import michael.linker.rewater.data.local.db.user.model.UserSessionTokenDataModel;
import michael.linker.rewater.data.local.db.user.model.UserTokensDataModel;

public class UserData implements IUserData {
    private final AppDatabase mDatabase;

    public UserData() {
        mDatabase = DatabaseConfiguration.getDatabase();
    }

    @Override
    public void addUser(final UserDataModel model) {
        final User user = new User();
        user.username = model.getUsername();
        user.active = false;
        mDatabase.userDao().insert(user);

        final SessionToken sessionToken = new SessionToken();
        sessionToken.ownerUsername = model.getUsername();
        sessionToken.token = model.getTokens().getSessionToken().getToken();
        sessionToken.validUntil = model.getTokens().getSessionToken().getValidUntilAsString();
        mDatabase.sessionTokenDao().insert(sessionToken);

        final AuthToken authToken = new AuthToken();
        authToken.ownerUsername = model.getUsername();
        authToken.token = model.getTokens().getAuthToken().getToken();
        mDatabase.authTokenDao().insert(authToken);
    }

    @Override
    public void updateSessionTokenForActiveUser(UserSessionTokenDataModel sessionTokenModel)
            throws UserDataNotFoundException {
        final User activeUser = this.getActiveUser();

        final SessionToken sessionToken = new SessionToken();
        sessionToken.ownerUsername = activeUser.username;
        sessionToken.token = sessionTokenModel.getToken();
        sessionToken.validUntil = sessionTokenModel.getValidUntilAsString();
        mDatabase.sessionTokenDao().insert(sessionToken);
    }

    @Override
    public void updateTokensForActiveUser(UserTokensDataModel tokensModel)
            throws UserDataNotFoundException {
        final User activeUser = this.getActiveUser();
        final String activeUserUsername = activeUser.username;

        final SessionToken sessionToken = new SessionToken();
        sessionToken.ownerUsername = activeUserUsername;
        sessionToken.token = tokensModel.getSessionToken().getToken();
        sessionToken.validUntil = tokensModel.getSessionToken().getValidUntilAsString();
        mDatabase.sessionTokenDao().insert(sessionToken);

        final AuthToken authToken = new AuthToken();
        authToken.ownerUsername = activeUserUsername;
        authToken.token = tokensModel.getAuthToken().getToken();
        mDatabase.authTokenDao().insert(authToken);
    }

    @Override
    public void activateUser(String username) throws UserDataNotFoundException {
        final User user = mDatabase.userDao().findByUsername(username);
        if (user != null) {
            user.active = true;
            mDatabase.userDao().update(user);
        } else {
            throw new UserDataNotFoundException(
                    "The user with username: " + username + " was not found");
        }
    }

    @Override
    public void deactivateActiveUser() {
        try {
            final User user = this.getActiveUser();
            user.active = false;
            mDatabase.userDao().update(user);
        } catch (UserDataNotFoundException ignored) {
        }
    }

    @Override
    public void removeUser(String username) {
        mDatabase.userDao().deleteByUsername(username);
    }

    @Override
    public UserDataModel getActiveUserModel() throws UserDataNotFoundException {
        final User user = this.getActiveUser();
        final SessionToken userSessionToken = mDatabase.sessionTokenDao().findActive();
        final AuthToken userAuthToken = mDatabase.authTokenDao().findActive();
        return new UserDataModel(
                user.username,
                new UserTokensDataModel(
                        new UserAuthTokenDataModel(userAuthToken.token),
                        new UserSessionTokenDataModel(
                                userSessionToken.token,
                                Instant.parse(userSessionToken.validUntil)
                        )
                )
        );
    }

    @Override
    public UserSessionTokenDataModel getSessionTokenOfActiveUser()
            throws UserDataNotFoundException {
        final SessionToken userSessionToken = mDatabase.sessionTokenDao().findActive();
        if (userSessionToken != null) {
            return new UserSessionTokenDataModel(
                    userSessionToken.token,
                    Instant.parse(userSessionToken.validUntil)
            );
        } else {
            throw new UserDataNotFoundException("Active user was not found");
        }
    }

    @Override
    public UserAuthTokenDataModel getAuthTokenOfActiveUser() throws UserDataNotFoundException {
        final AuthToken authToken = mDatabase.authTokenDao().findActive();
        if (authToken != null) {
            return new UserAuthTokenDataModel(authToken.token);
        } else {
            throw new UserDataNotFoundException("Active user was not found");
        }
    }

    private User getActiveUser() throws UserDataNotFoundException {
        final User activeUser = mDatabase.userDao().findActive();
        if (activeUser == null) {
            throw new UserDataNotFoundException("Active user was not found");
        }
        return activeUser;
    }
}
