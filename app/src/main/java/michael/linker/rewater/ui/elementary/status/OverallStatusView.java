package michael.linker.rewater.ui.elementary.status;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.ImageView;

import michael.linker.rewater.R;
import michael.linker.rewater.data.model.Status;
import michael.linker.rewater.data.res.StatusColorsProvider;
import michael.linker.rewater.data.model.DetailedStatusModel;
import michael.linker.rewater.ui.elementary.ICustomView;

public class OverallStatusView implements ICustomView {
    private final View mView;
    private final ImageView mImageView;

    public OverallStatusView(final View view) {
        mView = view;
        mImageView = view.findViewById(R.id.overall_status_image);
    }

    public void setStatus(final DetailedStatusModel detailedStatusModel) {
        final Status worstStatus = Status.getWorstStatus(detailedStatusModel.toList());
        mImageView.setImageTintList(
                ColorStateList.valueOf(StatusColorsProvider.getColorForStatus(worstStatus)));
    }

    @Override
    public View getViewInstance() {
        return mView;
    }

    @Override
    public void setVisibility(final int visibility) {
        mView.setVisibility(visibility);
    }
}
