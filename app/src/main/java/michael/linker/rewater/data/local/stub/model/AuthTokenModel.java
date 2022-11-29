package michael.linker.rewater.data.local.stub.model;

public class AuthTokenModel {
    private final String mToken;

    public AuthTokenModel(String token) {
        mToken = token;
    }

    public String getToken() {
        return mToken;
    }
}
