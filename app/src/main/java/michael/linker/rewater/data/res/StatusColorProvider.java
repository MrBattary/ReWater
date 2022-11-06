package michael.linker.rewater.data.res;

import java.util.Map;

import michael.linker.rewater.R;
import michael.linker.rewater.data.model.Status;

public class StatusColorProvider {
    private static final Map<Status, Integer> STATUS_INTEGER_MAP;
    private static final Integer UNDEFINED_COLOR = ColorProvider.getColor(R.color.white);

    static {
        STATUS_INTEGER_MAP = Map.of(Status.OK, ColorProvider.getColor(R.color.positive_color),
                Status.WARNING, ColorProvider.getColor(R.color.warning_color),
                Status.DEFECT, ColorProvider.getColor(R.color.negative_color),
                Status.UNDEFINED, UNDEFINED_COLOR);
    }

    public static Integer getColorForStatus(final Status status) {
        final Integer color = STATUS_INTEGER_MAP.get(status);
        if (color == null) {
            return UNDEFINED_COLOR;
        }
        return color;
    }
}
