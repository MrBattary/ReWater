package michael.linker.rewater.assist.comparator;

import java.util.Map;
import java.util.Objects;

import michael.linker.rewater.model.status.DetailedStatusModel;
import michael.linker.rewater.constant.Status;

public class StatusComparator {
    private static final Map<Status, Integer> STATUS_WEIGHT_MAP;
    private static final Integer UNDEFINED_STATUS_WEIGHT = 3;
    private static final Integer OK_STATUS_WEIGHT = 2;
    private static final Integer WARNING_STATUS_WEIGHT = 1;
    private static final Integer DEFECT_STATUS_WEIGHT = 0;

    static {
        STATUS_WEIGHT_MAP = Map.of(
                Status.UNDEFINED, UNDEFINED_STATUS_WEIGHT,
                Status.OK, OK_STATUS_WEIGHT,
                Status.WARNING, WARNING_STATUS_WEIGHT,
                Status.DEFECT, DEFECT_STATUS_WEIGHT);
    }

    public static boolean isEquals(final Status first, final Status second) {
        return Objects.equals(STATUS_WEIGHT_MAP.get(first), STATUS_WEIGHT_MAP.get(second));
    }

    public static boolean isFirstWorseThanSecond(final Status first, final Status second) {
        return getStatusWeight(first) < getStatusWeight(second);
    }

    public static boolean isSecondWorseThanFirst(final Status first, final Status second) {
        if (first == Status.UNDEFINED) {
            return false;
        }
        return getStatusWeight(first) > getStatusWeight(second);
    }

    public static Status getWorstStatus(final DetailedStatusModel detailedStatusModel) {
        Status worstStatus = Status.UNDEFINED;
        for (Status currentStatus : detailedStatusModel.toList()) {
            if (currentStatus != Status.UNDEFINED &&
                    isFirstWorseThanSecond(currentStatus, worstStatus)) {
                worstStatus = currentStatus;
            }
        }
        return worstStatus;
    }

    private static Integer getStatusWeight(final Status status) {
        Integer weight = STATUS_WEIGHT_MAP.get(status);
        if (weight == null) {
            return UNDEFINED_STATUS_WEIGHT;
        }
        return weight;
    }
}
