package michael.linker.rewater.data.repository.user;

import michael.linker.rewater.data.repository.user.model.UserSignInRepositoryModel;
import michael.linker.rewater.data.repository.user.model.UserSignUpRepositoryModel;

public interface IUserRepository {
    /**
     * Sign in.
     *
     * @param model The username and password pair
     * @throws UsersRepositoryAccessDeniedException If an incorrect username-password pair is
     *                                              entered
     */
    void signIn(UserSignInRepositoryModel model) throws UsersRepositoryAccessDeniedException;

    /**
     * Sign up.
     *
     * @param model The username, email and password
     * @throws UsersRepositoryAlreadyExistsException If the username or email address is already
     *                                               registered in the system
     */
    void signUp(UserSignUpRepositoryModel model) throws UsersRepositoryAlreadyExistsException;
}
