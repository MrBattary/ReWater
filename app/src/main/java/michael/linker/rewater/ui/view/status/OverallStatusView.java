package michael.linker.rewater.ui.view.status;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.ImageView;

import java.util.Map;

import michael.linker.rewater.R;
import michael.linker.rewater.model.status.DetailedStatusModel;
import michael.linker.rewater.assist.comparator.StatusComparator;
import michael.linker.rewater.assist.provider.ColorProvider;
import michael.linker.rewater.constant.Status;
import michael.linker.rewater.ui.view.IView;

public class OverallStatusView implements IView {
    private final View mView;
    private final ImageView mImageView;
    private final Map<Status, Integer> mStatusColorMap;

    public OverallStatusView(final View view) {
        mView = view;
        mStatusColorMap = initStatusColorMap();
        mImageView = view.findViewById(R.id.overall_status_image);
    }

    public void setStatus(final DetailedStatusModel detailedStatusModel) {
        final Status worstStatus = StatusComparator.getWorstStatus(detailedStatusModel);
        mImageView.setImageTintList(ColorStateList.valueOf(mStatusColorMap.get(worstStatus)));
    }

    @Override
    public View getViewInstance() {
        return mView;
    }

    private Map<Status, Integer> initStatusColorMap() {
        return ColorProvider.getStatusColorMap();
    }
}
