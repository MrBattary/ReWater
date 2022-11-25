package michael.linker.rewater.data.repository.user.model;

public class SignInUserRepositoryModel {
    private final String mUsername, mPassword;

    public SignInUserRepositoryModel(
            final String username,
            final String password) {
        mUsername = username;
        mPassword = password;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getPassword() {
        return mPassword;
    }
}
