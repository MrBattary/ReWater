package michael.linker.rewater.data.local.db.user;

import michael.linker.rewater.data.local.db.user.model.UserAuthTokenDataModel;
import michael.linker.rewater.data.local.db.user.model.UserDataModel;
import michael.linker.rewater.data.local.db.user.model.UserSessionTokenDataModel;
import michael.linker.rewater.data.local.db.user.model.UserTokensDataModel;

/**
 * Wrapper around user database part
 */
public interface IUserData {
    /**
     * Add the inactive user with tokens to the internal storage.
     * Replace on conflict
     *
     * @param model model with user data and access tokens
     */
    void addUser(UserDataModel model);

    /**
     * Updates session token for the active user.
     *
     * @param sessionTokenModel model with session token
     * @throws UserDataNotFoundException if the active user was not found
     */
    void updateSessionTokenForActiveUser(UserSessionTokenDataModel sessionTokenModel)
            throws UserDataNotFoundException;

    /**
     * Updates auth and session tokens for the active user.
     *
     * @param tokensModel model with both auth and session tokens
     * @throws UserDataNotFoundException if the active user was not found
     */
    void updateTokensForActiveUser(UserTokensDataModel tokensModel)
            throws UserDataNotFoundException;

    /**
     * Deactivates already active user then activate user with provided username.
     *
     * @param username username
     * @throws UserDataNotFoundException if the user was not found
     */
    void activateUser(String username) throws UserDataNotFoundException;

    /**
     * Deactivates active user
     */
    void deactivateActiveUser();

    /**
     * Deactivates already active user then removes it from the internal storage with all tokens.
     *
     * @param username username
     */
    void removeUser(String username);

    /**
     * Getter.
     *
     * @return user model
     * @throws UserDataNotFoundException if the active user was not found
     */
    UserDataModel getActiveUserModel() throws UserDataNotFoundException;

    /**
     * Session token getter.
     *
     * @return session token model
     * @throws UserDataNotFoundException if the active user was not found
     */
    UserSessionTokenDataModel getSessionTokenOfActiveUser() throws UserDataNotFoundException;

    /**
     * Session token getter.
     *
     * @return auth token model
     * @throws UserDataNotFoundException if the active user was not found
     */
    UserAuthTokenDataModel getAuthTokenOfActiveUser() throws UserDataNotFoundException;
}
