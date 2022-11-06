package michael.linker.rewater.data.res;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;

import androidx.core.content.res.ResourcesCompat;

public class DrawablesProvider {
    private static final Resources RESOURCES = App.getRes();
    private static final Resources.Theme THEME = App.getInstance().getTheme();

    /**
     * Retrieve a drawable for a particular resource ID.
     *
     * @param id resource ID
     * @return drawable
     */
    public static Drawable getDrawable(final int id) {
        return ResourcesCompat.getDrawable(RESOURCES, id, THEME);
    }

    /**
     * Retrieve a vector drawable for a particular resource ID.
     *
     * @param id resource ID
     * @return vector drawable
     */
    public static VectorDrawable getVectorDrawable(final int id) {
        return (VectorDrawable) ResourcesCompat.getDrawable(RESOURCES, id, THEME);
    }
}
