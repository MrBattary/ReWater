package michael.linker.rewater.assist.provider;

import android.content.res.Resources;

import michael.linker.rewater.assist.App;

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
}
