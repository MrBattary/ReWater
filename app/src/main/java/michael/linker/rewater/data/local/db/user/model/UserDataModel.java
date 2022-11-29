package michael.linker.rewater.data.local.db.user.model;

public class UserDataModel {
    private final String mUsername;
    private final Boolean mIsActive;
    private final UserTokensDataModel mTokensDataModel;

    /**
     * Active user by default
     */
    public UserDataModel(
            String username,
            UserTokensDataModel tokensDataModel) {
        mUsername = username;
        mIsActive = true;
        mTokensDataModel = tokensDataModel;
    }

    public String getUsername() {
        return mUsername;
    }

    public Boolean getActive() {
        return mIsActive;
    }

    public UserTokensDataModel getTokens() {
        return mTokensDataModel;
    }
}
