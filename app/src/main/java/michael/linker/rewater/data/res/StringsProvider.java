package michael.linker.rewater.data.res;

import android.content.res.Resources;

import michael.linker.rewater.core.App;

public class StringsProvider {
    private static final Resources RESOURCES = App.getRes();

    public static String getString(final int id) {
        return RESOURCES.getString(id);
    }
}
