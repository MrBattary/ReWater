package michael.linker.rewater.ui.navigation.item.home;

import android.graphics.drawable.VectorDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.Map;

import michael.linker.rewater.R;
import michael.linker.rewater.constant.Status;
import michael.linker.rewater.ui.navigation.NavigationFaultException;
import michael.linker.rewater.assist.provider.DimensionsProvider;
import michael.linker.rewater.assist.provider.DrawablesProvider;

public class HomeItem implements IHomeItem {
    private final Map<Status, VectorDrawable> statusIcons;
    private final View iconItemView;
    private Status currentStatus;

    public HomeItem(final View navigationBarItemView) {
        iconItemView = navigationBarItemView.findViewById(
                com.google.android.material.R.id.navigation_bar_item_icon_container);
        this.initIconContainer();
        statusIcons = initStatusIconsMap();
        this.setSummaryStatus(Status.OK);
    }

    @Override
    public Status getSummaryStatus() {
        return currentStatus;
    }

    @Override
    public void setSummaryStatus(final Status newStatus) throws NavigationFaultException {
        VectorDrawable icon = statusIcons.get(newStatus);
        if (icon == null) {
            throw new NavigationFaultException(
                    "The icon for the " + newStatus.name() + " status was not found.");
        }
        iconItemView.setForeground(icon);
        currentStatus = newStatus;
    }

    private Map<Status, VectorDrawable> initStatusIconsMap() {
        Map<Status, VectorDrawable> initMap = new HashMap<>();
        initMap.put(Status.OK, DrawablesProvider.getVectorDrawable(
                R.drawable.ic_home_fine));
        initMap.put(Status.WARNING, DrawablesProvider.getVectorDrawable(
                R.drawable.ic_home_warning));
        initMap.put(Status.DEFECT, DrawablesProvider.getVectorDrawable(
                R.drawable.ic_home_defect));
        return initMap;
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
