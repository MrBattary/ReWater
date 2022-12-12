package michael.linker.rewater.ui.elementary.history;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import michael.linker.rewater.R;
import michael.linker.rewater.data.model.status.HistoryStatus;
import michael.linker.rewater.data.res.StatusColorsProvider;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.ui.elementary.ICustomView;
import michael.linker.rewater.ui.elementary.history.model.HistoryCardDateTimeBaseModel;
import michael.linker.rewater.ui.elementary.history.model.HistoryCardNetworkScheduleDateTimeModel;
import michael.linker.rewater.ui.elementary.history.model.HistoryCardScheduleDateTimeModel;
import michael.linker.rewater.ui.elementary.parententity.ParentEntityView;

public class HistoryCardView implements ICustomView {
    private View mRootView;
    private CardView mCardView;
    private ViewGroup mParentsView;
    private TextView mScheduleView, mDateTimeView;
    private ParentEntityView mNetworkView;

    public HistoryCardView(View rootView) {
        this.initViews(rootView);

    }

    /**
     * Set card content and adapt it's view.
     *
     * @param model HistoryCardDateTimeBaseModel
     *              OR
     *              HistoryCardScheduleDateTimeModel
     *              OR
     *              HistoryCardNetworkScheduleDateTimeModel
     */
    public void setCardContent(HistoryCardDateTimeBaseModel model) {
        this.setCardBackgroundColor(model.getHistoryStatus());
        mDateTimeView.setText(model.getTime());
        mParentsView.setVisibility(View.GONE);

        if (model instanceof HistoryCardScheduleDateTimeModel) {
            mScheduleView.setText(
                    ((HistoryCardNetworkScheduleDateTimeModel) model).getScheduleName());
            mParentsView.setVisibility(View.VISIBLE);
            mNetworkView.setVisibility(View.GONE);
        }

        if (model instanceof HistoryCardNetworkScheduleDateTimeModel) {
            mNetworkView.setParentEntity(
                    ((HistoryCardNetworkScheduleDateTimeModel) model).getScheduleName());
            mNetworkView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View getViewInstance() {
        return mRootView;
    }

    @Override
    public void setVisibility(int visibility) {
        mRootView.setVisibility(visibility);
    }

    private void initViews(View rootView) {
        mRootView = rootView;
        mCardView = rootView.findViewById(R.id.history_event_card);
        mParentsView = rootView.findViewById(R.id.history_card_parents);
        mScheduleView = rootView.findViewById(R.id.history_card_parents_schedule);
        mDateTimeView = rootView.findViewById(R.id.history_card_date_time);
        mNetworkView = new ParentEntityView(
                rootView.findViewById(R.id.history_card_parents_network),
                StringsProvider.getString(R.string.parent_network_not_found));
    }

    private void setCardBackgroundColor(HistoryStatus historyStatus) {
        mCardView.setCardBackgroundColor(
                StatusColorsProvider.getBackgroundColorForHistoryStatus(historyStatus));
    }
}
