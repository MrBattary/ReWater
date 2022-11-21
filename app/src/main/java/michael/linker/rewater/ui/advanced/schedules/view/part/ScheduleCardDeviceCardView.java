package michael.linker.rewater.ui.advanced.schedules.view.part;

import android.view.View;

import michael.linker.rewater.ui.advanced.devices.model.DeviceWithoutParentsUiModel;

public class ScheduleCardDeviceCardView extends ScheduleDeviceCardView {
    public ScheduleCardDeviceCardView(View parentView) {
        super(parentView);

        mDetachButton.setVisibility(View.GONE);
    }

    public void setUiModel(final DeviceWithoutParentsUiModel deviceUiModel) {
        mScheduleNameTextView.setText(deviceUiModel.getName());
        mDetailStatusView.setStatus(deviceUiModel.getStatus());
    }
}
