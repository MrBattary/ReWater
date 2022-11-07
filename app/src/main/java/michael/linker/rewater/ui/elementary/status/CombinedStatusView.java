package michael.linker.rewater.ui.elementary.status;

import android.view.View;

import michael.linker.rewater.R;
import michael.linker.rewater.ui.model.DetailedStatusModel;
import michael.linker.rewater.ui.elementary.ICustomView;

public class CombinedStatusView implements ICustomView {
    private final View mView;
    private final OverallStatusView mOverallStatusView;
    private final DetailStatusView mDetailStatusView;

    public CombinedStatusView(final View view) {
        mView = view;
        mOverallStatusView = new OverallStatusView(view.findViewById(
                R.id.combined_status_overall_view));
        mDetailStatusView = new DetailStatusView(view.findViewById(
                R.id.combined_status_detailed_view));
        displayCompact();
    }

    public void displayDetailed() {
        mOverallStatusView.getViewInstance().setVisibility(View.GONE);
        mDetailStatusView.getViewInstance().setVisibility(View.VISIBLE);
    }

    public void displayCompact() {
        mOverallStatusView.getViewInstance().setVisibility(View.VISIBLE);
        mDetailStatusView.getViewInstance().setVisibility(View.GONE);
    }

    public void setStatus(final DetailedStatusModel detailedStatusModel) {
        mOverallStatusView.setStatus(detailedStatusModel);
        mDetailStatusView.setStatus(detailedStatusModel);
    }

    public OverallStatusView getOverallStatusView() {
        return mOverallStatusView;
    }

    public DetailStatusView getDetailedStatusView() {
        return mDetailStatusView;
    }

    @Override
    public View getViewInstance() {
        return mView;
    }
}
