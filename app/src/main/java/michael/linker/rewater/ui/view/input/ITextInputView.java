package michael.linker.rewater.ui.view.input;

import java.util.List;

public interface ITextInputView extends IInputView {
    /**
     * Get current text from the text input.
     *
     * @return text from input field
     * @throws InputNotAllowedException if the entered text does not comply with the rules
     */
    String getText() throws InputNotAllowedException;

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
     * Set limit rule.
     * If the text length is more than provided limit, an error will be returned when
     * receiving the text.
     *
     * @param limit max length of the input text
     * @param errorMsg message to display if an error occurs
     */
    void setLimit(final int limit, final String errorMsg);
}
