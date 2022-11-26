package michael.linker.rewater.data.local.stub.model;

public class FullUserModel extends SignInUserModel {
    private final String mEmail;

    public FullUserModel(String username, String password, String email) {
        super(username, password);
        mEmail = email;
    }

    public String getEmail() {
        return mEmail;
    }
}
