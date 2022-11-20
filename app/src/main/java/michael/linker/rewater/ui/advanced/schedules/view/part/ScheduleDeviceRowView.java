package michael.linker.rewater.ui.advanced.schedules.view.part;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import michael.linker.rewater.R;
import michael.linker.rewater.data.repository.devices.model.DeviceModel;
import michael.linker.rewater.ui.elementary.ICustomView;
import michael.linker.rewater.ui.elementary.status.DetailStatusView;

public class ScheduleDeviceRowView implements ICustomView {
    private final CardView mCardParentView;
    private DetailStatusView mDetailStatusView;
    private TextView mScheduleNameTextView;
    private ImageButton mDetachButton;

    /**
     * Constructor for the schedule device row without the detach button.
     *
     * @param cardParentView Card view of the view_schedule_device_row
     * @param deviceModel    Model of the provided device
     */
    public ScheduleDeviceRowView(final CardView cardParentView, final DeviceModel deviceModel) {
        mCardParentView = cardParentView;
        this.initFields();
        this.initData(deviceModel);

        mDetachButton.setVisibility(View.GONE);
    }

    /**
     * Constructor for the schedule device row with the active detach button.
     *
     * @param cardParentView  Card view of the view_schedule_device_row
     * @param deviceModel     Model of the provided device
     * @param onClickListener OnClickListener for the detach button
     */
    public ScheduleDeviceRowView(
            final CardView cardParentView,
            final DeviceModel deviceModel,
            final View.OnClickListener onClickListener) {
        mCardParentView = cardParentView;
        this.initFields();
        this.initData(deviceModel);

        mDetachButton.setOnClickListener(onClickListener);
    }

    private void initFields() {
        mDetailStatusView = new DetailStatusView(
                mCardParentView.findViewById(R.id.schedule_device_row_status));
        mScheduleNameTextView = mCardParentView.findViewById(R.id.schedule_device_row_device_name);

        mDetachButton = mCardParentView.findViewById(R.id.schedule_device_row_detach);
    }

    private void initData(final DeviceModel deviceModel) {
        mScheduleNameTextView.setText(deviceModel.getName());
        mDetailStatusView.setStatus(deviceModel.getStatus());
    }

    @Override
    public View getViewInstance() {
        return mCardParentView;
    }

    @Override
    public void setVisibility(final int visibility) {
        mCardParentView.setVisibility(visibility);
    }
}
