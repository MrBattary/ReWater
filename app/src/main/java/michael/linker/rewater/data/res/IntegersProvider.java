package michael.linker.rewater.data.res;

import android.content.res.Resources;

import michael.linker.rewater.core.App;

public class IntegersProvider {
    private static final Resources RESOURCES = App.getRes();

    public static Integer getInteger(final int id) {
        return RESOURCES.getInteger(id);
    }
}
