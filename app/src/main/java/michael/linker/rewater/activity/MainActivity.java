package michael.linker.rewater.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import michael.linker.rewater.R;
import michael.linker.rewater.data.res.DrawablesProvider;
import michael.linker.rewater.data.res.StringsProvider;
import michael.linker.rewater.data.web.api.common.CommonApi;
import michael.linker.rewater.data.web.gate.HttpGateProvider;
import michael.linker.rewater.data.web.gate.IHttpGate;
import michael.linker.rewater.databinding.ActivityMainBinding;
import michael.linker.rewater.ui.advanced.navigation.view.HomeNavigationView;
import michael.linker.rewater.ui.elementary.dialog.IDialog;
import michael.linker.rewater.ui.elementary.dialog.two.TwoChoicesDialogModel;
import michael.linker.rewater.ui.elementary.dialog.two.TwoChoicesWarningDialog;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private NavController mNavController;

    private IHttpGate mHttpGate;
    private CommonApi mApi;

    private IDialog mExitDialog, mInternetLostDialog, mServerLostDialog;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mHttpGate = HttpGateProvider.getHttpGate();
        mApi = new CommonApi();

        this.initNavigation();
        this.initDialogs();
        this.observeStatus();
        new HomeNavigationView(this, binding);
    }

    @Override
    public boolean onSupportNavigateUp() {
        final NavController navController = mNavController;
        final AppBarConfiguration appBarConfiguration = mAppBarConfiguration;
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (!getOnBackPressedDispatcher().hasEnabledCallbacks()) {
            mExitDialog.show();
        } else {
            super.onBackPressed();
        }
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
            throw new RuntimeException();
        }
        return ((NavHostFragment) fragment).getNavController();
    }

    private void initDialogs() {
        mExitDialog = new TwoChoicesWarningDialog(this,
                new TwoChoicesDialogModel(
                        DrawablesProvider.getDrawable(R.drawable.ic_info),
                        StringsProvider.getString(R.string.title_exit),
                        StringsProvider.getString(R.string.dialog_exit),
                        StringsProvider.getString(R.string.button_exit),
                        StringsProvider.getString(R.string.button_cancel)
                ),
                (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    ActivityGate.finishApplication(this);
                },
                (dialogInterface, i) -> dialogInterface.dismiss()
        );
        mInternetLostDialog = new TwoChoicesWarningDialog(this,
                new TwoChoicesDialogModel(
                        DrawablesProvider.getDrawable(R.drawable.ic_warning),
                        StringsProvider.getString(R.string.title_error),
                        StringsProvider.getString(R.string.dialog_internet_connection_lost),
                        StringsProvider.getString(R.string.button_reconnect),
                        StringsProvider.getString(R.string.button_exit)
                ),
                (dialogInterface, i) -> checkIfInternetIsAccessible(),
                (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    ActivityGate.finishApplication(this);
                }
        );
        mServerLostDialog = new TwoChoicesWarningDialog(this,
                new TwoChoicesDialogModel(
                        DrawablesProvider.getDrawable(R.drawable.ic_warning),
                        StringsProvider.getString(R.string.title_error),
                        StringsProvider.getString(R.string.dialog_server_connection_lost),
                        StringsProvider.getString(R.string.button_reconnect),
                        StringsProvider.getString(R.string.button_exit)
                ),
                (dialogInterface, i) -> checkIfServerIsAccessible(),
                (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    ActivityGate.finishApplication(this);
                }
        );
    }

    private void observeStatus() {
        mHttpGate.getStatusObserver().isServerAccessible().observe(this, isConnected -> {
            if (!isConnected) {
                mServerLostDialog.show();
                mInternetLostDialog.dismiss();
            } else {
                mServerLostDialog.dismiss();
            }
        });
        mHttpGate.getStatusObserver().isInternetAccessible().observe(this, isConnected -> {
            if (!isConnected) {
                mInternetLostDialog.show();
                mServerLostDialog.dismiss();
            } else {
                mInternetLostDialog.dismiss();
            }
        });
    }

    private void checkIfServerIsAccessible() {
        Single.fromCallable(() -> mApi.pingServer())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(b -> {
                    if (!b) {
                        Single.fromCallable(() -> mApi.pingInternet())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(b2 -> {
                                    if(!b2) {
                                        mServerLostDialog.dismiss();
                                        mInternetLostDialog.show();
                                    }
                                }, e -> {
                                });
                    }
                }, e -> {
                });
    }

    private void checkIfInternetIsAccessible() {
        Single.fromCallable(() -> mApi.pingInternet())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(b -> {
                    if(b) {
                        Single.fromCallable(() -> mApi.pingServer())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(b2 -> {
                                    if(!b2) {
                                        mInternetLostDialog.dismiss();
                                        mServerLostDialog.show();
                                    }
                                }, e -> {
                                });
                    }
                }, e -> {
                });
    }
}