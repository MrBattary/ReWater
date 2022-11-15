package michael.linker.rewater.ui.elementary.input.text;

import android.text.Editable;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import michael.linker.rewater.ui.elementary.input.InputNotAllowedException;

public class TextInputView implements ITextInputView {
    private final TextInputLayout mTextInputLayout;
    private final TextInputEditText mTextInput;
    private List<String> mBlacklist;
    private String mBlacklistErrorMsg;
    private Integer mMaxLimit, mMinLimit;
    private String mMaxLimitErrorMsg, mMinLimitErrorMsg;

    public TextInputView(final TextInputLayout textInputLayout, final TextInputEditText textInput) {
        mTextInputLayout = textInputLayout;
        mTextInput = textInput;
        mBlacklist = new ArrayList<>();
        mMaxLimit = null;
        mMinLimit = null;
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
    public String getText() throws InputNotAllowedException {
        final String text = this.getTextForce() ;
        if (mMinLimit != null && text.length() < mMinLimit) {
            mTextInputLayout.setError(mMinLimitErrorMsg);
            throw new InputNotAllowedException("The text size is less than the allowed value!");
        }
        if (mMaxLimit != null && text.length() > mMaxLimit) {
            mTextInputLayout.setError(mMaxLimitErrorMsg);
            throw new InputNotAllowedException("The text size is more than the allowed value!");
        }
        if (mBlacklist != null && mBlacklist.size() > 0) {
            for (String blacklistText : mBlacklist) {
                if (text.equals(blacklistText)) {
                    mTextInputLayout.setError(mBlacklistErrorMsg);
                    throw new InputNotAllowedException(
                            "The entered text matches the value in the blacklist!");
                }
            }
        }
        return text;
    }

    @Override
    public String getTextForce() {
        final Editable editable = mTextInput.getText();
        if (editable != null) {
            return editable.toString();
        } else {
            return  "";
        }
    }

    @Override
    public void setText(final String text) {
        mTextInput.setText(text);
    }

    @Override
    public void setBlacklist(final List<String> blacklist, final String errorMsg) {
        mBlacklist = blacklist;
        mBlacklistErrorMsg = errorMsg;
    }

    @Override
    public boolean isTextBlacklisted(final String text) {
        return mBlacklist.contains(text);
    }

    @Override
    public void removeFromBlackList(final String allowedText) {
        final int positionOfAllowedText = mBlacklist.indexOf(allowedText);
        if (positionOfAllowedText >= 0) {
            mBlacklist.remove(positionOfAllowedText);
        }
    }

    @Override
    public void setMaxLimit(final int limit, final String errorMsg) {
        mMaxLimit = limit;
        mMaxLimitErrorMsg = errorMsg;
    }

    @Override
    public void setMinLimit(int limit, String errorMsg) {
        mMinLimit = limit;
        mMinLimitErrorMsg = errorMsg;
    }
}
