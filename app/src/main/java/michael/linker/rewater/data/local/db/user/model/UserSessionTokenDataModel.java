package michael.linker.rewater.data.local.db.user.model;

public class UserSessionTokenDataModel {
    private final String mToken;
    private final String mValidUntil;

    public UserSessionTokenDataModel(final String token, final String validUntil) {
        mToken = token;
        mValidUntil = validUntil;
    }

    public String getToken() {
        return mToken;
    }

    public String getValidUntil() {
        return mValidUntil;
    }

    public String getValidUntilAsString() {
        return mValidUntil.toString();
    }
}
