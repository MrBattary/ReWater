package michael.linker.rewater.data.repository.user;

import michael.linker.rewater.data.repository.user.model.SignInUserRepositoryModel;
import michael.linker.rewater.data.repository.user.model.SignUpUserRepositoryModel;

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
}
