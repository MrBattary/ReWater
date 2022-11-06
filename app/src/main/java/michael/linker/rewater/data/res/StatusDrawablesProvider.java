package michael.linker.rewater.assist.provider;

import android.graphics.drawable.VectorDrawable;

import java.util.Map;

import michael.linker.rewater.R;
import michael.linker.rewater.constant.Status;

public class StatusDrawablesProvider {
    private static final Map<Status, VectorDrawable> ENERGY_STATUS_ICONS_MAP;
    private static final VectorDrawable UNDEFINED_ENERGY_STATUS_ICON =
            DrawablesProvider.getVectorDrawable(R.drawable.ic_battery_neutral);

    static {
        ENERGY_STATUS_ICONS_MAP = Map.of(Status.OK,
                DrawablesProvider.getVectorDrawable(R.drawable.ic_battery_fine),
                Status.WARNING,
                DrawablesProvider.getVectorDrawable(R.drawable.ic_battery_warning),
                Status.DEFECT,
                DrawablesProvider.getVectorDrawable(R.drawable.ic_battery_defect),
                Status.UNDEFINED, UNDEFINED_ENERGY_STATUS_ICON);
    }

    public static VectorDrawable getIconForEnergyStatus(final Status status) {
        VectorDrawable icon = ENERGY_STATUS_ICONS_MAP.get(status);
        if (status == null) {
            return UNDEFINED_ENERGY_STATUS_ICON;
        }
        return icon;
    }
}
