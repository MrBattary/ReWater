package michael.linker.rewater.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class Status {
    // Available status options
    public static final Status UNDEFINED;
    public static final Status OK;
    public static final Status WARNING;
    public static final Status DEFECT;
    // Names of the status options
    private static final String UNDEFINED_STATUS_NAME = "UNDEFINED";
    private static final String OK_STATUS_NAME = "OK";
    private static final String WARNING_STATUS_NAME = "WARNING";
    private static final String DEFECT_STATUS_NAME = "DEFECT";
    // Weight of the status options
    private static final Integer UNDEFINED_STATUS_WEIGHT = 3;
    private static final Integer OK_STATUS_WEIGHT = 2;
    private static final Integer WARNING_STATUS_WEIGHT = 1;
    private static final Integer DEFECT_STATUS_WEIGHT = 0;
    // Map for valueOf
    private static final Map<String, Status> STATUS_MAP;

    static {
        UNDEFINED = new Status(UNDEFINED_STATUS_NAME, UNDEFINED_STATUS_WEIGHT);
        OK = new Status(OK_STATUS_NAME, OK_STATUS_WEIGHT);
        WARNING = new Status(WARNING_STATUS_NAME, WARNING_STATUS_WEIGHT);
        DEFECT = new Status(DEFECT_STATUS_NAME, DEFECT_STATUS_WEIGHT);
        STATUS_MAP = Map.of(
                UNDEFINED_STATUS_NAME, UNDEFINED,
                OK_STATUS_NAME, OK,
                WARNING_STATUS_NAME, WARNING,
                DEFECT_STATUS_NAME, DEFECT);
    }

    private final String mStatusName;
    private final Integer mStatusWeight;

    public Status(final String statusName, final Integer statusWeight) {
        mStatusName = statusName;
        mStatusWeight = statusWeight;
    }

    public boolean worse(final Status status) {
        return this.mStatusWeight < status.mStatusWeight;
    }

    public boolean better(final Status status) {
        return this.mStatusWeight > status.mStatusWeight;
    }

    @Override
    public boolean equals(@Nullable final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Status) {
            return Objects.equals(this.mStatusName, ((Status) obj).mStatusName);
        } else {
            return false;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return mStatusName;
    }

    public static Status getWorstStatus(final List<Status> statusList) {
        Status worstStatus = Status.UNDEFINED;
        for (Status status : statusList) {
            if (status != Status.UNDEFINED && status.worse(worstStatus)) {
                worstStatus = status;
            }
        }
        return worstStatus;
    }


    public static Status valueOf(final String statusName) {
        return STATUS_MAP.getOrDefault(statusName, UNDEFINED);
    }
}
