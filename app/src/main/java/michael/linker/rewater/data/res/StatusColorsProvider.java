package michael.linker.rewater.data.res;

import java.util.Map;

import michael.linker.rewater.R;
import michael.linker.rewater.data.model.status.Status;

public class StatusColorsProvider {
    private static final Map<Status, Integer> STATUS_INTEGER_MAP;
    private static final Integer UNDEFINED_COLOR = ColorsProvider.getColor(R.color.white);

    static {
        STATUS_INTEGER_MAP = Map.of(Status.OK, ColorsProvider.getColor(R.color.positive),
                Status.WARNING, ColorsProvider.getColor(R.color.warning),
                Status.DEFECT, ColorsProvider.getColor(R.color.negative),
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
