package michael.linker.rewater.data.local.stub.model;

public class SessionTokenModel extends AuthTokenModel {
    private final String mValidUntil;

    public SessionTokenModel(String token, String validUntil) {
        super(token);
        mValidUntil = validUntil;
    }

    public String getValidUntil() {
        return mValidUntil;
    }
}
