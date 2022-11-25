package michael.linker.rewater.data.repository.user.model;

public class UserSignUpRepositoryModel extends UserSignInRepositoryModel {
    private final String mEmail;

    public UserSignUpRepositoryModel(
            final String username,
            final String password,
            final String email) {
        super(username, password);
        mEmail = email;
    }

    public String getEmail() {
        return mEmail;
    }
}
