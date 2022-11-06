package michael.linker.rewater.data.model;

import androidx.annotation.Nullable;

import java.util.Objects;

public final class Status {
    public static final Status UNDEFINED;
    public static final Status OK;
    public static final Status WARNING;
    public static final Status DEFECT;
    private static final Integer UNDEFINED_STATUS_WEIGHT = 3;
    private static final Integer OK_STATUS_WEIGHT = 2;
    private static final Integer WARNING_STATUS_WEIGHT = 1;
    private static final Integer DEFECT_STATUS_WEIGHT = 0;

    static {
        OK = new Status("OK", OK_STATUS_WEIGHT);
        WARNING = new Status("WARNING", WARNING_STATUS_WEIGHT);
        DEFECT = new Status("DEFECT", DEFECT_STATUS_WEIGHT);
        UNDEFINED = new Status("UNDEFINED", UNDEFINED_STATUS_WEIGHT);
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

    public boolean equals(@Nullable final Status status) {
        if (status == null) {
            return false;
        }
        return Objects.equals(this.mStatusName, status.mStatusName);
    }
}
