package michael.linker.rewater.data.repository.user.model;

public class SignUpUserRepositoryModel extends SignInUserRepositoryModel {
    private final String mEmail;

    public SignUpUserRepositoryModel(
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
