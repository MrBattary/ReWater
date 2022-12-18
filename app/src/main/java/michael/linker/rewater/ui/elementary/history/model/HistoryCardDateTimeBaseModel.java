package michael.linker.rewater.ui.elementary.history.model;

import michael.linker.rewater.data.model.status.HistoryStatus;
import michael.linker.rewater.ui.advanced.devices.model.DeviceHistoryUiModel;
import michael.linker.rewater.ui.advanced.schedules.model.ScheduleHistoryUiModel;

public class HistoryCardDateTimeBaseModel {
    private final String mTime;
    private final HistoryStatus mHistoryStatus;

    public HistoryCardDateTimeBaseModel(
            String time,
            HistoryStatus historyStatus) {
        mTime = time;
        mHistoryStatus = historyStatus;
    }

    public HistoryCardDateTimeBaseModel(ScheduleHistoryUiModel uiModel) {
        mTime = uiModel.getTime();
        mHistoryStatus = uiModel.getHistoryStatus();
    }

    public HistoryCardDateTimeBaseModel(DeviceHistoryUiModel uiModel) {
        mTime = uiModel.getTime();
        mHistoryStatus = uiModel.getHistoryStatus();
    }

    public String getTime() {
        return mTime;
    }

    public HistoryStatus getHistoryStatus() {
        return mHistoryStatus;
    }
}
