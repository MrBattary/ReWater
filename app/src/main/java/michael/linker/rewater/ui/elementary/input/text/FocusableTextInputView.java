package michael.linker.rewater.ui.elementary.input.text;

import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class FocusableTextInputView extends TextInputView implements IFocusableTextInputView {
    private final View mFocusableView;

    public FocusableTextInputView(
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
