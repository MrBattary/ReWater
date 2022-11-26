package michael.linker.rewater.data.local.db.user.model;

import java.time.Instant;

public class UserSessionTokenDataModel {
    private final String mToken;
    private final Instant mValidUntil;

    public UserSessionTokenDataModel(final String token, final Instant validUntil) {
        mToken = token;
        mValidUntil = validUntil;
    }

    public String getToken() {
        return mToken;
    }

    public Instant getValidUntil() {
        return mValidUntil;
    }

    public String getValidUntilAsString() {
        return mValidUntil.toString();
    }
}
