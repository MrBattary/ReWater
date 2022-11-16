package michael.linker.rewater.ui.elementary.input.number.integer;

import android.text.Editable;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import michael.linker.rewater.ui.elementary.input.InputNotAllowedException;

public class IntegerNumberInputView implements IIntegerNumberInput {
    private final TextInputLayout mTextInputLayout;
    private final TextInputEditText mTextInput;
    private Integer mMinLimit, mMaxLimit;
    private String mMinLimitErrorMsg, mMaxLimitErrorMsg;

    public IntegerNumberInputView(final TextInputLayout textInputLayout,
            final TextInputEditText textInput) {
        mTextInputLayout = textInputLayout;
        mTextInput = textInput;
        mMinLimit = null;
        mMaxLimit = null;
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
    public Integer getNumber() throws InputNotAllowedException {
        final Integer number = this.getNumberForce();
        if (mMinLimit != null && number < mMinLimit) {
            mTextInputLayout.setError(mMinLimitErrorMsg);
            throw new InputNotAllowedException("The number is less than the allowed value!");
        }
        if (mMaxLimit != null && number > mMaxLimit) {
            mTextInputLayout.setError(mMaxLimitErrorMsg);
            throw new InputNotAllowedException("The number is greater than the allowed value!");
        }
        return number;
    }

    @Override
    public Integer getNumberForce() {
        final Editable text = mTextInput.getText();
        if (text != null) {
            return Integer.getInteger(text.toString());
        }
        return 0;
    }

    @Override
    public void setNumber(Integer number) {
        mTextInput.setText(String.valueOf(number));
    }

    @Override
    public void setMaxLimit(final Integer limit, final String errorMsg) {
        mMaxLimit = limit;
        mMaxLimitErrorMsg = errorMsg;
    }

    @Override
    public void setMinLimit(final Integer limit, final String errorMsg) {
        mMinLimit = limit;
        mMinLimitErrorMsg = errorMsg;
    }
}
