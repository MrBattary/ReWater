package michael.linker.rewater.ui.advanced.schedules.view.part;

import android.view.View;

import michael.linker.rewater.ui.advanced.devices.model.DeviceIdNameUiModel;

public class UpdateScheduleDeviceCardView extends ScheduleDeviceCardView {
    /**
     * Constructor for the schedule device row with the active detach button, but without the
     * detailed status view.
     *
     * @param parentView Card view of the view_schedule_device_row
     */
    public UpdateScheduleDeviceCardView(final View parentView) {
        super(parentView);

        mDetailStatusView.setVisibility(View.GONE);
    }

    public void setUiModel(final DeviceIdNameUiModel deviceUiModel) {
        mScheduleNameTextView.setText(deviceUiModel.getName());
    }

    public void setOnClickListener(final View.OnClickListener onClickListener) {
        mDetachButton.setOnClickListener(onClickListener);
    }
}
