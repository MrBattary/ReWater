package michael.linker.rewater.ui.advanced.navigation;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import michael.linker.rewater.R;
import michael.linker.rewater.data.model.Status;
import michael.linker.rewater.databinding.ActivityMainBinding;
import michael.linker.rewater.ui.advanced.navigation.home.HomeItem;
import michael.linker.rewater.ui.advanced.navigation.home.IHomeItem;

public class Navigation implements INavigation {
    private static final int HOME_ITEM_POSITION_IN_MENU = 2;
    private AppBarConfiguration mAppBarConfiguration;
    private NavController mNavController;
    private IHomeItem mHomeItem;

    public Navigation(final AppCompatActivity activity) throws NavigationFaultException {
        this.initNavigation(activity);
        this.initHomeNavigationView(activity);
    }

    @Override
    public Status getSummaryStatusFromHomeItem() {
        return mHomeItem.getSummaryStatus();
    }

    @Override
    public void setSummaryStatusForHomeItem(Status status) throws NavigationFaultException {
        mHomeItem.setSummaryStatus(status);
    }

    @Override
    public AppBarConfiguration getAppBarConfiguration() {
        return mAppBarConfiguration;
    }

    @Override
    public NavController getNavController() {
        return mNavController;
    }

    private void initNavigation(final AppCompatActivity activity) throws NavigationFaultException {
        bindMainActivity(activity);
        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.navigation_view);

        AppBarConfiguration appBarConfiguration = buildAppBarConfiguration();
        mNavController = findNavController(activity);
        mAppBarConfiguration = new AppBarConfiguration.Builder(mNavController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(activity, mNavController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, mNavController);
    }

    private void initHomeNavigationView(final AppCompatActivity activity) {
        final BottomNavigationView bottomNavigationView = activity.findViewById(
                R.id.navigation_view);
        final Menu menu = bottomNavigationView.getMenu();
        final MenuItem menuItem = menu.getItem(HOME_ITEM_POSITION_IN_MENU);
        final View navigationBarItemView = bottomNavigationView.findViewById(menuItem.getItemId());
        mHomeItem = new HomeItem(navigationBarItemView);
    }

    private void bindMainActivity(final AppCompatActivity activity) {
        ActivityMainBinding binding = ActivityMainBinding.inflate(activity.getLayoutInflater());
        activity.setContentView(binding.getRoot());
    }

    private AppBarConfiguration buildAppBarConfiguration() {
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        return new AppBarConfiguration.Builder(
                R.id.navigation_networks,
                R.id.navigation_devices,
                R.id.navigation_home,
                R.id.navigation_profile,
                R.id.navigation_settings).build();
    }

    private NavController findNavController(final AppCompatActivity activity)
            throws NavigationFaultException {
        Fragment fragment = activity.getSupportFragmentManager().findFragmentById(
                R.id.navigation_host_fragment);
        if (!(fragment instanceof NavHostFragment)) {
            throw new NavigationFaultException(
                    "Activity " + this + " does not have a NavHostFragment");
        }
        return ((NavHostFragment) fragment).getNavController();
    }
}
