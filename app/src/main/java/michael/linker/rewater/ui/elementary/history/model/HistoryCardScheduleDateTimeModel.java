package michael.linker.rewater.ui.elementary.history.model;

import michael.linker.rewater.data.model.IdNameModel;
import michael.linker.rewater.data.model.status.HistoryStatus;

public class HistoryCardScheduleDateTimeModel extends HistoryCardDateTimeBaseModel {
    private final String mScheduleName;

    public HistoryCardScheduleDateTimeModel(
            String time,
            HistoryStatus historyStatus,
            IdNameModel scheduleIdName) {
        super(time, historyStatus);
        mScheduleName = scheduleIdName.getName();
    }

    public String getScheduleName() {
        return mScheduleName;
    }
}
