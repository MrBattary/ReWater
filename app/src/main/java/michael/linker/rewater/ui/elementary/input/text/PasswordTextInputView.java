package michael.linker.rewater.ui.elementary.input.text;

import android.text.Editable;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import michael.linker.rewater.R;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.ui.elementary.input.InputNotAllowedException;
import michael.linker.rewater.util.security.HashFunction;

public class PasswordTextInputView implements IPasswordTextInputView {
    /**
     * ^                 | start-of-string
     * (?=.*[0-9])       | a digit must occur at least once
     * (?=.*[a-z])       | a lower case letter must occur at least once
     * (?=.*[A-Z])       | an upper case letter must occur at least once
     * (?=\S+$)          | no whitespace allowed in the entire string
     * .{6,}             | anything, at least six places though
     * $                 | end-of-string
     */
    private static final String sPASSWORD_POLICY_DIGIT =
            "^(?=.*[0-9])$";
    private static final String sPASSWORD_POLICY_LOWER_CASE =
            "^(?=.*[a-z])$";
    private static final String sPASSWORD_POLICY_UPPER_CASE =
            "^(?=.*[A-Z])$";
    private static final String sPASSWORD_POLICY_NO_WHITESPACE =
            "^(?=\\S+$)$";
    private static final Integer sPASSWORD_POLICY_LENGTH = 6;

    private final TextInputLayout mTextInputLayout;
    private final TextInputEditText mTextInput;

    public PasswordTextInputView(
            final TextInputLayout textInputLayout,
            final TextInputEditText textInput) {
        mTextInputLayout = textInputLayout;
        mTextInput = textInput;
    }

    @Override
    public View getViewInstance() {
        return mTextInput;
    }

    @Override
    public void setVisibility(final int visibility) {
        mTextInputLayout.setVisibility(visibility);
    }

    @Override
    public void setOnFocusChangeListener(View.OnFocusChangeListener listener) {
        mTextInput.setOnFocusChangeListener(listener);
    }

    @Override
    public String getPasswordHash() {
        return HashFunction.hash(this.getText(), HashFunction.SHA_256);
    }

    @Override
    public String validateAndGetPasswordHash() throws InputNotAllowedException {
        final String passwordPlainText = this.getText();
        if (passwordPlainText.length() <= sPASSWORD_POLICY_LENGTH) {
            throw new InputNotAllowedException(
                    StringsProvider.getString(R.string.input_error_password_min_length));
        }
        if (!passwordPlainText.matches(sPASSWORD_POLICY_DIGIT)) {
            throw new InputNotAllowedException(
                    StringsProvider.getString(R.string.input_error_password_digit));
        }
        if (!passwordPlainText.matches(sPASSWORD_POLICY_LOWER_CASE)) {
            throw new InputNotAllowedException(
                    StringsProvider.getString(R.string.input_error_password_lower_case));
        }
        if (!passwordPlainText.matches(sPASSWORD_POLICY_UPPER_CASE)) {
            throw new InputNotAllowedException(
                    StringsProvider.getString(R.string.input_error_password_upper_case));
        }
        if (!passwordPlainText.matches(sPASSWORD_POLICY_NO_WHITESPACE)) {
            throw new InputNotAllowedException(
                    StringsProvider.getString(R.string.input_error_password_no_whitespace));
        }
        return HashFunction.hash(passwordPlainText, HashFunction.SHA_256);
    }

    private String getText() {
        final Editable editable = mTextInput.getText();
        if (editable != null) {
            return editable.toString();
        } else {
            return "";
        }
    }
}
