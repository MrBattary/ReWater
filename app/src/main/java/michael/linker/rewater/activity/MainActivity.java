package michael.linker.rewater.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import michael.linker.rewater.R;
import michael.linker.rewater.databinding.ActivityMainBinding;
import michael.linker.rewater.ui.advanced.navigation.view.HomeNavigationView;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private NavController mNavController;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.initNavigation();
        new HomeNavigationView(this, binding);
    }

    @Override
    public boolean onSupportNavigateUp() {
        final NavController navController = mNavController;
        final AppBarConfiguration appBarConfiguration = mAppBarConfiguration;
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void initNavigation() {
        BottomNavigationView bottomNavigationView = this.findViewById(R.id.bottom_navigation);
        mNavController = findNavController(this);
        mAppBarConfiguration = new AppBarConfiguration.Builder(mNavController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, mNavController,
                this.buildAppBarConfiguration());
        NavigationUI.setupWithNavController(bottomNavigationView, mNavController);
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
            throws RuntimeException {
        Fragment fragment = activity.getSupportFragmentManager().findFragmentById(
                R.id.main_navigation_host_fragment);
        if (!(fragment instanceof NavHostFragment)) {
            throw new RuntimeException(
                    "Activity " + this + " does not have a NavHostFragment");
        }
        return ((NavHostFragment) fragment).getNavController();
    }
}