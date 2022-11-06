package michael.linker.rewater;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import michael.linker.rewater.ui.advanced.navigation.INavigation;
import michael.linker.rewater.ui.advanced.navigation.Navigation;

public class MainActivity extends AppCompatActivity {
    private INavigation mINavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mINavigation = new Navigation(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        final NavController navController = mINavigation.getNavController();
        final AppBarConfiguration appBarConfiguration = mINavigation.getAppBarConfiguration();
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}