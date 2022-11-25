package michael.linker.rewater.ui.elementary.input.text;

import michael.linker.rewater.ui.elementary.IFocusable;
import michael.linker.rewater.ui.elementary.input.IInputView;
import michael.linker.rewater.ui.elementary.input.InputNotAllowedException;

public interface IPasswordTextInputView extends IInputView, IFocusable {
    /**
     * Get the password hash.
     *
     * @return hashed password the text input
     */
    String getPasswordHash();

    /**
     * Get a password hash that matches the password policy.
     *
     * @return hashed password from text input
     * @throws InputNotAllowedException if the password does not comply with the password policy
     */
    String validateAndGetPasswordHash() throws InputNotAllowedException;
}
