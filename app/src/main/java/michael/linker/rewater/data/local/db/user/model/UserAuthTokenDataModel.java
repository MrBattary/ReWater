package michael.linker.rewater.data.local.db.user.model;

public class UserAuthTokenDataModel {
    private final String mToken;

    public UserAuthTokenDataModel(final String token) {
        mToken = token;
    }

    public String getToken() {
        return mToken;
    }
}
