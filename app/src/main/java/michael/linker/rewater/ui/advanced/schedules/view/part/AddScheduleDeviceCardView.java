package michael.linker.rewater.ui.advanced.schedules.view.part;

import android.view.View;

import michael.linker.rewater.ui.advanced.devices.model.DeviceIdNameUiModel;

public class AddScheduleDeviceCardView extends ScheduleDeviceCardView {
    /**
     * Constructor for the schedule device row with the active detach button.
     *
     * @param parentView      Card view of the view_schedule_device_row
     * @param onClickListener OnClickListener for the detach button
     */
    public AddScheduleDeviceCardView(
            final View parentView,
            final View.OnClickListener onClickListener) {
        super(parentView);

        mDetailStatusView.setVisibility(View.GONE);
        mDetachButton.setOnClickListener(onClickListener);
    }

    public void setUiModel(final DeviceIdNameUiModel deviceUiModel) {
        mScheduleNameTextView.setText(deviceUiModel.getName());
    }
}
