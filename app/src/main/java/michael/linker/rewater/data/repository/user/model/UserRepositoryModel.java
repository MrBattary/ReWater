package michael.linker.rewater.data.repository.user.model;

public class UserRepositoryModel {
    private final String mUsername;

    public UserRepositoryModel(final String username) {
        mUsername = username;
    }

    public String getUsername() {
        return mUsername;
    }
}
