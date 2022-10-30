package michael.linker.rewater.assist.comparator;

import java.util.Map;

import michael.linker.rewater.model.status.StatusModel;
import michael.linker.rewater.constant.Status;

public class StatusComparator {
    private static final Map<Status, Integer> STATUS_WEIGHT_MAP;

    static {
        STATUS_WEIGHT_MAP = Map.of(Status.OK, 2, Status.WARNING, 1, Status.DEFECT, 0);
    }

    public static boolean isEquals(final Status first, final Status second) {
        return STATUS_WEIGHT_MAP.get(first) == STATUS_WEIGHT_MAP.get(second);
    }

    public static boolean isFirstWorseThanSecond(final Status first, final Status second) {
        return STATUS_WEIGHT_MAP.get(first) < STATUS_WEIGHT_MAP.get(second);
    }

    public static boolean isSecondWorseThanFirst(final Status first, final Status second) {
        return STATUS_WEIGHT_MAP.get(first) > STATUS_WEIGHT_MAP.get(second);
    }

    public static Status getWorstStatus(final StatusModel statusModel) {
        Status worstStatus = Status.OK;
        for (Status currentStatus : statusModel.toList()) {
            if (isFirstWorseThanSecond(currentStatus, worstStatus)) {
                worstStatus = currentStatus;
            }
        }
        return worstStatus;
    }
}
