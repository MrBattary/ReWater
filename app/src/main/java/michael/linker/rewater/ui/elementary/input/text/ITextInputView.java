package michael.linker.rewater.ui.elementary.input.text;

import java.util.List;

import michael.linker.rewater.ui.elementary.input.IInputView;
import michael.linker.rewater.ui.elementary.input.InputNotAllowedException;

public interface ITextInputView extends IInputView {
    /**
     * Get current text from the text input.
     *
     * @return text from input field
     * @throws InputNotAllowedException if the entered text does not comply with the rules
     */
    String getText() throws InputNotAllowedException;

    /**
     * Get current text from the text input without checking any rules.
     *
     * @return text from input field
     */
    String getTextForce();

    /**
     * Set text in the text input.
     * @param text text.
     */
    void setText(final String text);

    /**
     * Set blacklist rule.
     * If the text is equal to any text from the blacklist, an error will be returned when
     * receiving the text.
     *
     * @param blacklist list of prohibited texts
     * @param errorMsg message to display if an error occurs
     */
    void setBlacklist(List<String> blacklist, final String errorMsg);

    /**
     * Check if the provided text in the blacklist.
     *
     * @param text text to check
     * @return true if blacklisted, false otherwise
     */
    boolean isTextBlacklisted(String text);

    /**
     * Remove text from the blacklist.
     *
     * @param allowedText word to remove
     */
    void removeFromBlackList(String allowedText);

    /**
     * Set max limit rule.
     * If the text length is more than provided limit, an error will be returned when
     * receiving the text.
     *
     * @param limit max length of the input text
     * @param errorMsg message to display if an error occurs
     */
    void setMaxLimit(final int limit, final String errorMsg);

    /**
     * Set min limit rule.
     * If the text length is less than provided limit, an error will be returned when
     * receiving the text.
     *
     * @param limit min length of the input text
     * @param errorMsg message to display if an error occurs
     */
    void setMinLimit(final int limit, final String errorMsg);
}
