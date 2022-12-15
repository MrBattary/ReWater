package michael.linker.rewater.activity.intent;

import android.content.Intent;

import michael.linker.rewater.util.communication.IIntent;

public class SignOutIntent implements IIntent<SignOutIntentModel> {
    public static final Boolean EXPECTED_SIGN_OUT = true;
    public static final Boolean UNEXPECTED_SIGN_OUT = false;

    public static final SignOutIntent INSTANCE = new SignOutIntent();

    @Override
    public Intent pack(SignOutIntentModel model, Intent intent) {
        return intent.putExtra(SignOutIntentModel.getIsExpectedSignOutKey(),
                model.getExpectedSignOutValue());
    }

    @Override
    public SignOutIntentModel unpack(Intent intent) {
        return new SignOutIntentModel(
                intent.getBooleanExtra(SignOutIntentModel.getIsExpectedSignOutKey(),
                        UNEXPECTED_SIGN_OUT));
    }
}
