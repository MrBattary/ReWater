package michael.linker.rewater.data.res.status;

import java.util.Map;

import michael.linker.rewater.R;
import michael.linker.rewater.data.model.status.Status;
import michael.linker.rewater.data.res.ColorsProvider;

public class StatusColorsProvider {
    private static final Map<Status, Integer> STATUS_COLOR_MAP;
    private static final Map<Status, Integer> STATUS_BACKGROUND_COLOR_MAP;
    private static final Integer UNDEFINED_COLOR = ColorsProvider.getColor(R.color.undefined);

    static {
        STATUS_COLOR_MAP = Map.of(Status.OK, ColorsProvider.getColor(R.color.positive_primary),
                Status.WARNING, ColorsProvider.getColor(R.color.warning_primary),
                Status.DEFECT, ColorsProvider.getColor(R.color.negative_primary),
                Status.UNDEFINED, UNDEFINED_COLOR);
        STATUS_BACKGROUND_COLOR_MAP = Map.of(
                Status.OK, ColorsProvider.getColor(R.color.positive_secondary),
                Status.WARNING, ColorsProvider.getColor(R.color.warning_secondary),
                Status.DEFECT, ColorsProvider.getColor(R.color.negative_secondary),
                Status.UNDEFINED, UNDEFINED_COLOR);
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
}
