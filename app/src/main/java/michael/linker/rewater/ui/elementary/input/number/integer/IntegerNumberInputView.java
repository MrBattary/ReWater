package michael.linker.rewater.ui.elementary.input.number.integer;

import android.text.Editable;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import michael.linker.rewater.R;
import michael.linker.rewater.data.res.StringsProvider;
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
        try {
            final Integer number = this.getNumberForce();
            if (mMinLimit != null && number < mMinLimit) {
                mTextInputLayout.setError(mMinLimitErrorMsg);
                throw new InputNotAllowedException("The number is less than the allowed value!");
            }
            if (mMaxLimit != null && number > mMaxLimit) {
                mTextInputLayout.setError(mMaxLimitErrorMsg);
                throw new InputNotAllowedException("The number is greater than the allowed value!");
            }
            mTextInputLayout.setError(null);
            return number;
        } catch (NumberInputViewNotNumberException e) {
            mTextInputLayout.setError(
                    StringsProvider.getString(R.string.input_error_number_not_found));
            throw new InputNotAllowedException(e.getMessage());
        }
    }

    @Override
    public Integer getNumberForce() throws NumberInputViewNotNumberException {
        final Editable text = mTextInput.getText();
        if (text != null && !text.toString().equals("")) {
            return Integer.parseInt(text.toString());
        }
        throw new NumberInputViewNotNumberException(
                "Text is empty and cannot be converted to the integer!");
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
