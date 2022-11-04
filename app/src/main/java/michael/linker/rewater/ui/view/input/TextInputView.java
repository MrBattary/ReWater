package michael.linker.rewater.ui.view.input;

import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class TextInputView implements ITextInputView {
    private final TextInputLayout mTextInputLayout;
    private final EditText mTextInput;
    private List<String> mBlacklist;
    private String mBlacklistErrorMsg;
    private Integer mLimit;
    private String mLimitErrorMsg;

    public TextInputView(final TextInputLayout textInputLayout, final EditText textInput) {
        mTextInputLayout = textInputLayout;
        mTextInput = textInput;
        mBlacklist = null;
        mLimit = null;
    }

    @Override
    public View getViewInstance() {
        return mTextInput;
    }

    @Override
    public String getText() throws InputNotAllowedException {
        final String text = mTextInput.getText().toString();
        if (mLimit != null && text.length() > mLimit) {
            mTextInputLayout.setError(mLimitErrorMsg);
            throw new InputNotAllowedException("The size of the string exceeds the allowed value!");
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
    public void setBlacklist(final List<String> blacklist, final String errorMsg) {
        mBlacklist = blacklist;
        mBlacklistErrorMsg = errorMsg;
    }

    @Override
    public void setLimit(final int limit, final String errorMsg) {
        mLimit = limit;
        mLimitErrorMsg = errorMsg;
    }
}
