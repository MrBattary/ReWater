package michael.linker.rewater.data.local.db.user.model;

public class UserTokensDataModel {
    private final UserAuthTokenDataModel mAuthTokenModel;
    private final UserSessionTokenDataModel mSessionTokenModel;

    public UserTokensDataModel(
            final UserAuthTokenDataModel authTokenModel,
            final UserSessionTokenDataModel sessionTokenModel) {
        mAuthTokenModel = authTokenModel;
        mSessionTokenModel = sessionTokenModel;
    }

    public UserAuthTokenDataModel getAuthToken() {
        return mAuthTokenModel;
    }

    public UserSessionTokenDataModel getSessionToken() {
        return mSessionTokenModel;
    }
}
