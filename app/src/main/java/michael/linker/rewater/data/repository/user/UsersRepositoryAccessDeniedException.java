package michael.linker.rewater.data.repository.user;

import michael.linker.rewater.R;
import michael.linker.rewater.data.res.StringsProvider;

public class UsersRepositoryAccessDeniedException extends RuntimeException {
    private static final String sERROR_MSG = StringsProvider.getString(R.string.sign_in_failure);

    public UsersRepositoryAccessDeniedException(String message) {
        super(message);
    }
}
