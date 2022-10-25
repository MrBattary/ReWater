package michael.linker.rewater.application.provider;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;

import androidx.core.content.res.ResourcesCompat;

import michael.linker.rewater.application.App;

public class DrawablesProvider {
    private static final Resources resources = App.getRes();
    private static final Resources.Theme theme = App.getInstance().getTheme();

    /**
     * Retrieve a drawable for a particular resource ID.
     *
     * @param id resource ID
     * @return drawable
     */
    public static Drawable getDrawable(final int id) {
        return ResourcesCompat.getDrawable(resources, id, theme);
    }

    /**
     * Retrieve a vector drawable for a particular resource ID.
     *
     * @param id resource ID
     * @return vector drawable
     */
    public static VectorDrawable getVectorDrawable(final int id) {
        return (VectorDrawable) ResourcesCompat.getDrawable(resources, id, theme);
    }
}
