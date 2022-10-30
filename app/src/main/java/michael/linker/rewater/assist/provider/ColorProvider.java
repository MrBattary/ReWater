package michael.linker.rewater.assist.provider;

import android.content.res.Resources;

import java.util.HashMap;
import java.util.Map;

import michael.linker.rewater.R;
import michael.linker.rewater.assist.App;
import michael.linker.rewater.constant.Status;

public class ColorProvider {
    private static final Resources RESOURCES = App.getRes();
    private static final Resources.Theme THEME = App.getInstance().getTheme();

    /**
     * Retrieve a color for a particular resource ID.
     *
     * @param id resource ID
     * @return resource value
     */
    public static int getColor(final int id) {
        return RESOURCES.getColor(id, THEME);
    }

    public static Map<Status, Integer> getStatusColorMap() {
        Map<Status, Integer> initMap = new HashMap<>();
        initMap.put(Status.OK, ColorProvider.getColor(R.color.positive_color));
        initMap.put(Status.WARNING, ColorProvider.getColor(R.color.warning_color));
        initMap.put(Status.DEFECT, ColorProvider.getColor(R.color.negative_color));
        return initMap;
    }
}
