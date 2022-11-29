package michael.linker.rewater.data.repository.user;

import michael.linker.rewater.data.repository.user.model.SignInUserRepositoryModel;
import michael.linker.rewater.data.repository.user.model.SignUpUserRepositoryModel;
import michael.linker.rewater.data.repository.user.model.UserRepositoryModel;

public interface IUsersRepository {
    /**
     * Sign in.
     *
     * @param model The username and password pair
     * @throws UsersRepositoryAccessDeniedException If an incorrect username-password pair is
     *                                              entered
     */
    void signIn(SignInUserRepositoryModel model) throws UsersRepositoryAccessDeniedException;

    /**
     * Sign up.
     *
     * @param model The username, email and password
     * @throws UsersRepositoryAlreadyExistsException If the username or email address is already
     *                                               registered in the system
     */
    void signUp(SignUpUserRepositoryModel model) throws UsersRepositoryAlreadyExistsException;

    /**
     * Sign out for the active user.
     */
    void signOut();

    /**
     * Try to refresh internal session token using the auth token.
     * If this function does not work, the user must log in again to get a new auth token.
     *
     * @throws UsersRepositoryNotFoundException     If the auth token was not found
     * @throws UsersRepositoryAccessDeniedException If the authorization token is not accepted
     */
    void refreshSessionToken()
            throws UsersRepositoryNotFoundException, UsersRepositoryAccessDeniedException;


    /**
     * Get active user data model.
     *
     * @return username of the active user
     * @throws UsersRepositoryNotFoundException If the active user was not found
     */
    UserRepositoryModel getActiveUserData() throws UsersRepositoryNotFoundException;
}
