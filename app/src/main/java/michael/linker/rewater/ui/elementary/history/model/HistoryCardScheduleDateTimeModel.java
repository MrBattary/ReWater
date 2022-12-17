package michael.linker.rewater.ui.elementary.history.model;

import michael.linker.rewater.data.model.IdNameModel;
import michael.linker.rewater.data.model.status.HistoryStatus;
import michael.linker.rewater.ui.advanced.networks.model.NetworkHistoryUiModel;

public class HistoryCardScheduleDateTimeModel extends HistoryCardDateTimeBaseModel {
    private final String mScheduleName;

    public HistoryCardScheduleDateTimeModel(
            String time,
            HistoryStatus historyStatus,
            IdNameModel scheduleIdName) {
        super(time, historyStatus);
        mScheduleName = scheduleIdName.getName();
    }

    public HistoryCardScheduleDateTimeModel(NetworkHistoryUiModel model) {
        super(model.getTime(), model.getHistoryStatus());
        mScheduleName = model.getScheduleIdName().getName();
    }

    public String getScheduleName() {
        return mScheduleName;
    }
}
