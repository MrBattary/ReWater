package michael.linker.rewater.data.model.status;

import java.util.Map;

public final class HistoryStatus {
    public static final HistoryStatus SUCCESS;
    public static final HistoryStatus ERROR;

    private static final int SUCCESS_VALUE = 1;
    private static final int ERROR_VALUE = 0;

    private static final Map<Integer, HistoryStatus> HISTORY_STATUS_MAP;

    static {
        SUCCESS = new HistoryStatus(SUCCESS_VALUE);
        ERROR = new HistoryStatus(ERROR_VALUE);

        HISTORY_STATUS_MAP = Map.of(
                SUCCESS_VALUE, SUCCESS,
                ERROR_VALUE, ERROR
        );
    }

    private final int mValue;

    private HistoryStatus(final int value) {
        mValue = value;
    }

    public static HistoryStatus valueOf(int statusValue) {
        return HISTORY_STATUS_MAP.getOrDefault(statusValue, ERROR);
    }
}
