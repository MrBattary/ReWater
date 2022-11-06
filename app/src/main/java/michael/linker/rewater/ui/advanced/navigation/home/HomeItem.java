package michael.linker.rewater.ui.advanced.navigation.home;

import android.graphics.drawable.VectorDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import michael.linker.rewater.R;
import michael.linker.rewater.data.model.Status;
import michael.linker.rewater.data.res.DimensionsProvider;
import michael.linker.rewater.data.res.StatusDrawablesProvider;
import michael.linker.rewater.ui.advanced.navigation.NavigationFaultException;

public class HomeItem implements IHomeItem {
    private final View iconItemView;
    private Status currentStatus;

    public HomeItem(final View navigationBarItemView) {
        iconItemView = navigationBarItemView.findViewById(
                com.google.android.material.R.id.navigation_bar_item_icon_container);
        this.initIconContainer();
        this.setSummaryStatus(Status.OK);
    }

    @Override
    public Status getSummaryStatus() {
        return currentStatus;
    }

    @Override
    public void setSummaryStatus(final Status newStatus) throws NavigationFaultException {
        VectorDrawable icon = StatusDrawablesProvider.getIconForSummaryStatus(newStatus);
        if (icon == null) {
            throw new NavigationFaultException(
                    "The icon for the " + newStatus.toString() + " status was not found.");
        }
        iconItemView.setForeground(icon);
        currentStatus = newStatus;
    }

    private void initIconContainer() {
        // TODO (Battary): Wait for the response on the stackoverflow and adjust the size
        FrameLayout.LayoutParams iconItemViewParams =
                (FrameLayout.LayoutParams) iconItemView.getLayoutParams();
        iconItemViewParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                DimensionsProvider.getDpExtracted(R.dimen.navigation_home_icon_size),
                DimensionsProvider.getDisplayMetrics());
        iconItemViewParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                DimensionsProvider.getDpExtracted(R.dimen.navigation_home_icon_size),
                DimensionsProvider.getDisplayMetrics());
        iconItemViewParams.gravity = Gravity.CENTER;
        iconItemView.setLayoutParams(iconItemViewParams);
    }
}
