package michael.linker.rewater.ui.advanced.sign.model;

import android.os.Bundle;

import michael.linker.rewater.util.IBundle;

public class SignBundle implements IBundle<SignUiModel> {
    private static final String KEY_USERNAME = "SignBundle_mUsername";

    public static final SignBundle INSTANCE = new SignBundle();

    @Override
    public Bundle pack(SignUiModel model) {
        final Bundle bundle = new Bundle();
        bundle.putString(KEY_USERNAME, model.getUsername());
        return bundle;
    }

    @Override
    public SignUiModel unpack(Bundle bundle) {
        return new SignUiModel(bundle.getString(KEY_USERNAME));
    }
}
