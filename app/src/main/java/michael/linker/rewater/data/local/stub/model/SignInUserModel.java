package michael.linker.rewater.data.local.stub.model;

public class SignInUserModel {
    private final String mUsername;
    private final String mPassword;

    public SignInUserModel(String username, String password) {
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
