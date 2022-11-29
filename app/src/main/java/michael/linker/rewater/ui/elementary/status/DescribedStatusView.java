package michael.linker.rewater.ui.elementary.status;

import android.content.res.ColorStateList;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import michael.linker.rewater.data.model.status.Status;
import michael.linker.rewater.data.res.StatusColorsProvider;
import michael.linker.rewater.ui.elementary.text.status.IStatusStyledTextView;

public class DescribedStatusView implements IStatusStyledTextView {
    private final ViewGroup mParentView;
    private final ImageView mStatusImageView;
    private final IStatusStyledTextView mStatusStyledTextView;

    public DescribedStatusView(
            final ViewGroup parentView,
            final ImageView statusImageView,
            final IStatusStyledTextView statusStyledTextView) {
        mParentView = parentView;
        mStatusImageView = statusImageView;
        mStatusStyledTextView = statusStyledTextView;
    }

    @Override
    public View getViewInstance() {
        return mParentView;
    }

    @Override
    public void setVisibility(final int visibility) {
        mParentView.setVisibility(visibility);
    }

    @Override
    public void setText(final String text) {
        mStatusStyledTextView.setText(text);
    }

    @Override
    public void setText(final String text, final Status status) {
        mStatusStyledTextView.setText(text, status);
        mStatusImageView.setImageTintList(
                ColorStateList.valueOf(StatusColorsProvider.getColorForStatus(status)));
    }
}
