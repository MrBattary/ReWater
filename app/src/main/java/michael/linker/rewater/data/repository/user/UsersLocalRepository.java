package michael.linker.rewater.data.repository.user;

import michael.linker.rewater.data.repository.user.model.SignInUserRepositoryModel;
import michael.linker.rewater.data.repository.user.model.SignUpUserRepositoryModel;

public class UsersLocalRepository implements IUsersRepository {
    @Override
    public void signIn(SignInUserRepositoryModel model)
            throws UsersRepositoryAccessDeniedException {
    }

    @Override
    public void signUp(SignUpUserRepositoryModel model)
            throws UsersRepositoryAlreadyExistsException {
    }
}
