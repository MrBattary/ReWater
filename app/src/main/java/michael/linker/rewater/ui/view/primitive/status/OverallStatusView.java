package michael.linker.rewater.ui.view.primitive.status;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.ImageView;

import michael.linker.rewater.R;
import michael.linker.rewater.assist.comparator.StatusComparator;
import michael.linker.rewater.assist.provider.StatusColorProvider;
import michael.linker.rewater.constant.Status;
import michael.linker.rewater.model.local.status.DetailedStatusModel;
import michael.linker.rewater.ui.view.IView;

public class OverallStatusView implements IView {
    private final View mView;
    private final ImageView mImageView;

    public OverallStatusView(final View view) {
        mView = view;
        mImageView = view.findViewById(R.id.overall_status_image);
    }

    public void setStatus(final DetailedStatusModel detailedStatusModel) {
        final Status worstStatus = StatusComparator.getWorstStatus(detailedStatusModel);
        mImageView.setImageTintList(
                ColorStateList.valueOf(StatusColorProvider.getColorForStatus(worstStatus)));
    }

    @Override
    public View getViewInstance() {
        return mView;
    }
}