package michael.linker.rewater.data.res;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import michael.linker.rewater.core.App;

public class DimensionsProvider {
    private static final Resources RESOURCES = App.getRes();

    /**
     * Retrieve a dimensional for a particular resource ID.
     * Unit conversions are based on the current DisplayMetrics associated with the resources.
     *
     * @param id resource ID
     * @return resource value
     */
    public static int getDp(final int id) {
        return (int) RESOURCES.getDimension(id);
    }

    /**
     * Retrieve a extracted dp value for a particular resource ID.
     * E.g. for the resource with value 40dp method will return 40.
     *
     * @param id resource ID
     * @return resource value
     */
    public static int getDpExtracted(final int id) {
        return (int) (RESOURCES.getDimension(id) / RESOURCES.getDisplayMetrics().density);
    }

    /**
     * Retrieve a extracted sp value for a particular resource ID.
     * E.g. for the resource with value 40sp method will return 40.
     *
     * @param id resource ID
     * @return resource value
     */
    public static float getSpExtracted(final int id) {
        return (RESOURCES.getDimension(id) / RESOURCES.getDisplayMetrics().scaledDensity);
    }

    /**
     * Retrieve a display metrics of the device
     *
     * @return display metrics
     */
    public static DisplayMetrics getDisplayMetrics() {
        return RESOURCES.getDisplayMetrics();
    }

    /**
     * Retrieve a width of the display in dp
     *
     * @return float dp value
     */
    public static float getDisplayWidthInDp() {
        return RESOURCES.getDisplayMetrics().widthPixels / RESOURCES.getDisplayMetrics().density;
    }
}
