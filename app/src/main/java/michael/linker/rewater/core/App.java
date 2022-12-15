package michael.linker.rewater.core;

import android.app.Application;
import android.content.res.Resources;

/**
 * Application singleton for the access purpose
 */
public class App extends Application {
    private static App app;
    private static Resources res;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        res = getResources();
    }

    public static App getInstance() {
        return app;
    }

    public static Resources getRes() {
        return res;
    }
}
