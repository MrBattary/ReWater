package michael.linker.rewater.data.repository.user.model;

public class UserSignInRepositoryModel {
    private final String mUsername, mPassword;

    public UserSignInRepositoryModel(
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
