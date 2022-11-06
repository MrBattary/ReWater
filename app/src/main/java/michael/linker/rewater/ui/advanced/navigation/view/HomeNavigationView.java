package michael.linker.rewater.ui.advanced.navigation.view;

import android.graphics.drawable.VectorDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import michael.linker.rewater.R;
import michael.linker.rewater.data.res.DimensionsProvider;
import michael.linker.rewater.data.res.StatusDrawablesProvider;
import michael.linker.rewater.databinding.ActivityMainBinding;
import michael.linker.rewater.ui.advanced.navigation.viewmodel.HomeNavigationViewModel;

public class HomeNavigationView {
    private static final int HOME_ITEM_POSITION_IN_MENU = 2;
    private final ActivityMainBinding mBinding;
    private final View mHomeIconView;
    private final HomeNavigationViewModel mHomeNavigationViewModel;

    public HomeNavigationView(final AppCompatActivity activity, final ActivityMainBinding binding) {
        mBinding = binding;
        mHomeNavigationViewModel =
                new ViewModelProvider(activity).get(HomeNavigationViewModel.class);

        mHomeIconView = findHomeItem();
        initIconContainer(mHomeIconView);
        bindObservingStatus(activity);
    }

    private void bindObservingStatus(final AppCompatActivity activity) {
        mHomeNavigationViewModel.getCurrentStatus().observe(activity,
                status -> {
                    final VectorDrawable icon = StatusDrawablesProvider.getIconForSummaryStatus(
                            status);
                    mHomeIconView.setForeground(icon);
                });
    }

    private View findHomeItem() {
        final Menu menu = mBinding.bottomNavigation.getMenu();
        final MenuItem menuItem = menu.getItem(HOME_ITEM_POSITION_IN_MENU);
        final View navigationBarItemView =
                mBinding.bottomNavigation.findViewById(
                        menuItem.getItemId());
        return navigationBarItemView.findViewById(
                com.google.android.material.R.id.navigation_bar_item_icon_container);
    }

    private void initIconContainer(final View view) {
        // TODO (Battary): Wait for the response on the stackoverflow and adjust the size
        FrameLayout.LayoutParams iconItemViewParams =
                (FrameLayout.LayoutParams) view.getLayoutParams();
        iconItemViewParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                DimensionsProvider.getDpExtracted(R.dimen.navigation_home_icon_size),
                DimensionsProvider.getDisplayMetrics());
        iconItemViewParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                DimensionsProvider.getDpExtracted(R.dimen.navigation_home_icon_size),
                DimensionsProvider.getDisplayMetrics());
        iconItemViewParams.gravity = Gravity.CENTER;
        view.setLayoutParams(iconItemViewParams);
    }
}