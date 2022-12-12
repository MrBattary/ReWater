package michael.linker.rewater.data.res;

import java.util.Map;

import michael.linker.rewater.R;
import michael.linker.rewater.data.model.status.HistoryStatus;
import michael.linker.rewater.data.model.status.Status;

public class StatusColorsProvider {
    private static final Map<Status, Integer> STATUS_COLOR_MAP;
    private static final Map<Status, Integer> STATUS_BACKGROUND_COLOR_MAP;
    private static final Map<HistoryStatus, Integer> HISTORY_STATUS_BACKGROUND_COLOR_MAP;
    private static final Integer UNDEFINED_COLOR = ColorsProvider.getColor(R.color.white);

    static {
        STATUS_COLOR_MAP = Map.of(Status.OK, ColorsProvider.getColor(R.color.positive),
                Status.WARNING, ColorsProvider.getColor(R.color.warning),
                Status.DEFECT, ColorsProvider.getColor(R.color.negative),
                Status.UNDEFINED, UNDEFINED_COLOR);
        STATUS_BACKGROUND_COLOR_MAP = Map.of(
                Status.OK, ColorsProvider.getColor(R.color.background_positive),
                Status.WARNING, ColorsProvider.getColor(R.color.background_warning),
                Status.DEFECT, ColorsProvider.getColor(R.color.background_negative),
                Status.UNDEFINED, UNDEFINED_COLOR);
        HISTORY_STATUS_BACKGROUND_COLOR_MAP = Map.of(
                HistoryStatus.SUCCESS, ColorsProvider.getColor(R.color.background_positive),
                HistoryStatus.ERROR, ColorsProvider.getColor(R.color.background_negative)
        );
    }

    /**
     * Get color ID associated with the provided status.
     *
     * @param status status
     * @return ID of the color in the res
     */
    public static Integer getColorForStatus(final Status status) {
        final Integer color = STATUS_COLOR_MAP.get(status);
        if (color == null) {
            return UNDEFINED_COLOR;
        }
        return color;
    }

    /**
     * Get background color ID associated with the provided status.
     *
     * @param status status
     * @return ID of the background color in the res
     */
    public static Integer getBackgroundColorForStatus(final Status status) {
        final Integer color = STATUS_BACKGROUND_COLOR_MAP.get(status);
        if (color == null) {
            return UNDEFINED_COLOR;
        }
        return color;
    }

    /**
     * Get background color ID associated with the provided history status.
     *
     * @param status status
     * @return ID of the background color in the res
     */
    public static Integer getBackgroundColorForHistoryStatus(final HistoryStatus status) {
        final Integer color = HISTORY_STATUS_BACKGROUND_COLOR_MAP.get(status);
        if (color == null) {
            return UNDEFINED_COLOR;
        }
        return color;
    }
}
