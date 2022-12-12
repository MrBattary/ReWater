package michael.linker.rewater.data.res;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;

import java.util.Map;

import michael.linker.rewater.R;
import michael.linker.rewater.data.model.status.HistoryStatus;
import michael.linker.rewater.data.model.status.Status;

public class StatusDrawablesProvider {
    private static final Map<Status, VectorDrawable> ENERGY_STATUS_ICONS_MAP;
    private static final VectorDrawable UNDEFINED_ENERGY_STATUS_ICON =
            DrawablesProvider.getVectorDrawable(R.drawable.ic_battery_neutral);
    private static final Map<Status, VectorDrawable> SUMMARY_STATUS_ICONS_MAP;
    private static final VectorDrawable UNDEFINED_SUMMARY_STATUS_ICON =
            DrawablesProvider.getVectorDrawable(R.drawable.ic_home_fine);
    private static final Map<HistoryStatus, Drawable> HISTORY_STATUS_BACKGROUND_SHAPE_MAP;


    static {
        ENERGY_STATUS_ICONS_MAP = Map.of(
                Status.OK, DrawablesProvider.getVectorDrawable(R.drawable.ic_battery_fine),
                Status.WARNING, DrawablesProvider.getVectorDrawable(R.drawable.ic_battery_warning),
                Status.DEFECT, DrawablesProvider.getVectorDrawable(R.drawable.ic_battery_defect),
                Status.UNDEFINED, UNDEFINED_ENERGY_STATUS_ICON);

        SUMMARY_STATUS_ICONS_MAP = Map.of(
                Status.OK, DrawablesProvider.getVectorDrawable(R.drawable.ic_home_fine),
                Status.WARNING, DrawablesProvider.getVectorDrawable(R.drawable.ic_home_warning),
                Status.DEFECT, DrawablesProvider.getVectorDrawable(R.drawable.ic_home_defect),
                Status.UNDEFINED, UNDEFINED_SUMMARY_STATUS_ICON);
        HISTORY_STATUS_BACKGROUND_SHAPE_MAP = Map.of(
                HistoryStatus.SUCCESS,
                DrawablesProvider.getDrawable(R.drawable.shp_background_history_success),
                HistoryStatus.ERROR,
                DrawablesProvider.getDrawable(R.drawable.shp_background_history_error)
        );
    }

    public static VectorDrawable getIconForEnergyStatus(final Status status) {
        return getIconFromMapByStatus(
                status,
                ENERGY_STATUS_ICONS_MAP,
                UNDEFINED_ENERGY_STATUS_ICON);
    }

    public static VectorDrawable getIconForSummaryStatus(final Status status) {
        return getIconFromMapByStatus(
                status,
                SUMMARY_STATUS_ICONS_MAP,
                UNDEFINED_SUMMARY_STATUS_ICON);
    }

    public static Drawable getBackgroundForHistoryStatus(final HistoryStatus historyStatus) {
        return HISTORY_STATUS_BACKGROUND_SHAPE_MAP.get(historyStatus);
    }

    private static VectorDrawable getIconFromMapByStatus(
            final Status status,
            final Map<Status, VectorDrawable> map,
            final VectorDrawable defaultDrawable) {
        VectorDrawable icon = map.get(status);
        if (status == null) {
            return defaultDrawable;
        }
        return icon;
    }
}
