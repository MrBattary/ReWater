package michael.linker.rewater.ui.advanced.schedules.view.part;

import android.view.View;

import michael.linker.rewater.ui.advanced.devices.model.DeviceWithoutParentsUiModel;

public class ScheduleCardDeviceCardView extends ScheduleDeviceCardView {
    /**
     * Constructor for the schedule device row without the detach button, but with the status.
     *
     * @param parentView Card view of the view_schedule_device_row
     */
    public ScheduleCardDeviceCardView(View parentView) {
        super(parentView);

        mDetachButton.setVisibility(View.GONE);
    }

    public void setUiModel(final DeviceWithoutParentsUiModel deviceUiModel) {
        mScheduleNameTextView.setText(deviceUiModel.getName());
        mDetailStatusView.setStatus(deviceUiModel.getStatus());
    }
}
