package michael.linker.rewater.ui.advanced.schedules.view.part;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import michael.linker.rewater.R;
import michael.linker.rewater.ui.elementary.ICustomView;
import michael.linker.rewater.ui.elementary.status.DetailStatusView;

class ScheduleDeviceCardView implements ICustomView {
    protected CardView mCardView;
    protected DetailStatusView mDetailStatusView;
    protected TextView mScheduleNameTextView;
    protected ImageButton mDetachButton;

    public ScheduleDeviceCardView(final View parentView) {
        this.initFields(parentView);
    }

    private void initFields(final View view) {
        mCardView = view.findViewById(R.id.schedule_card);
        mDetailStatusView = new DetailStatusView(
                view.findViewById(R.id.schedule_device_row_status));
        mScheduleNameTextView = view.findViewById(R.id.schedule_device_row_device_name);

        mDetachButton = view.findViewById(R.id.schedule_device_row_detach);
    }

    @Override
    public View getViewInstance() {
        return mCardView;
    }

    @Override
    public void setVisibility(final int visibility) {
        mCardView.setVisibility(visibility);
    }
}
