package michael.linker.rewater.ui.elementary.input.number.integer;

import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class FocusableIntegerNumberInput extends IntegerNumberInputView implements
        IFocusableIntegerNumberInput {
    private final View mFocusableView;

    public FocusableIntegerNumberInput(
            final TextInputLayout textInputLayout,
            final TextInputEditText textInput) {
        super(textInputLayout, textInput);
        mFocusableView = textInput;
    }

    @Override
    public void setOnFocusChangeListener(View.OnFocusChangeListener listener) {
        mFocusableView.setOnFocusChangeListener(listener);
    }
}
