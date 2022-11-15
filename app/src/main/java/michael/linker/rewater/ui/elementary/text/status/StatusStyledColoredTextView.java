package michael.linker.rewater.ui.elementary.text.status;

import android.view.View;
import android.widget.TextView;

import michael.linker.rewater.data.model.Status;
import michael.linker.rewater.data.res.StatusColorsProvider;

public class StatusStyledColoredTextView implements IStatusStyledTextView {
    private final TextView mTextView;

    public StatusStyledColoredTextView(final TextView textView) {
        mTextView = textView;
    }

    @Override
    public View getViewInstance() {
        return mTextView;
    }

    @Override
    public void setVisibility(final int visibility) {
        mTextView.setVisibility(visibility);
    }

    @Override
    public void setText(final String text) {
        mTextView.setText(text);
    }

    @Override
    public void setText(final String text, final Status status) {
        this.setText(text);
        mTextView.setTextColor(StatusColorsProvider.getColorForStatus(status));
    }
}
