package michael.linker.rewater.ui.advanced.schedules.view.part;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import michael.linker.rewater.R;
import michael.linker.rewater.ui.advanced.devices.model.DeviceWithoutParentsUiModel;
import michael.linker.rewater.ui.elementary.ICustomView;
import michael.linker.rewater.ui.elementary.status.DetailStatusView;

public class ScheduleDeviceCardView implements ICustomView {
    private CardView mCardView;
    private DetailStatusView mDetailStatusView;
    private TextView mScheduleNameTextView;
    private ImageButton mDetachButton;

    /**
     * Constructor for the schedule device row without the detach button.
     *
     * @param parentView Card view of the view_schedule_device_row
     */
    public ScheduleDeviceCardView(final View parentView) {
        this.initFields(parentView);

        mDetachButton.setVisibility(View.GONE);
    }

    /**
     * Constructor for the schedule device row with the active detach button.
     *
     * @param parentView      Card view of the view_schedule_device_row
     * @param onClickListener OnClickListener for the detach button
     */
    public ScheduleDeviceCardView(
            final View parentView,
            final View.OnClickListener onClickListener) {
        this.initFields(parentView);

        mDetachButton.setOnClickListener(onClickListener);
    }

    private void initFields(final View view) {
        mCardView = view.findViewById(R.id.schedule_card);
        mDetailStatusView = new DetailStatusView(
                view.findViewById(R.id.schedule_device_row_status));
        mScheduleNameTextView = view.findViewById(R.id.schedule_device_row_device_name);

        mDetachButton = view.findViewById(R.id.schedule_device_row_detach);
    }

    public void setUiModel(final DeviceWithoutParentsUiModel deviceUiModel) {
        mScheduleNameTextView.setText(deviceUiModel.getName());
        mDetailStatusView.setStatus(deviceUiModel.getStatus());
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
