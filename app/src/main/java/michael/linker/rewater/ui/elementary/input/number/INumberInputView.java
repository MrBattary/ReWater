package michael.linker.rewater.ui.elementary.input.number;

import michael.linker.rewater.ui.elementary.input.IInputView;
import michael.linker.rewater.ui.elementary.input.InputNotAllowedException;

/**
 * @param <T> number type
 */
public interface INumberInputView<T> extends IInputView {
    /**
     * Get current from the text input.
     *
     * @return number from input field
     * @throws InputNotAllowedException if the entered number does not comply with the rules
     */
    T getNumber() throws InputNotAllowedException;

    /**
     * Get current number from the text input without checking any rules.
     *
     * @return number from input field
     */
    T getNumberForce();

    /**
     * Set number in the text input.
     *
     * @param number number
     */
    void setNumber(final T number);

    /**
     * Set max limit rule.
     * If the entered number is greater than this threshold, an error will be returned when the
     * number is received.
     *
     * @param limit    max value of the number
     * @param errorMsg message to display if an error occurs
     */
    void setMaxLimit(final T limit, final String errorMsg);

    /**
     * Set min limit rule.
     * If the entered number is less than this threshold, an error will be returned when the
     * number is received.
     *
     * @param limit    min value of the number
     * @param errorMsg message to display if an error occurs
     */
    void setMinLimit(final T limit, final String errorMsg);
}
