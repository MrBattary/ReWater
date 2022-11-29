package michael.linker.rewater.activity.intent;

public class SignOutIntentModel {
    private static final String IS_EXPECTED_SIGN_OUT_KEY = "SignOutIntentModel_isExpectedSignOut";
    private final Boolean isExpectedSignOut;

    public SignOutIntentModel(Boolean isExpectedSignOut) {
        this.isExpectedSignOut = isExpectedSignOut;
    }

    public static String getIsExpectedSignOutKey() {
        return IS_EXPECTED_SIGN_OUT_KEY;
    }

    public Boolean getExpectedSignOutValue() {
        return isExpectedSignOut;
    }
}
