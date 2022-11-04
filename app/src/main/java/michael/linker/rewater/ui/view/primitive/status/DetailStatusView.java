package michael.linker.rewater.ui.view.status;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.ImageView;

import michael.linker.rewater.R;
import michael.linker.rewater.assist.provider.StatusColorProvider;
import michael.linker.rewater.assist.provider.StatusDrawablesProvider;
import michael.linker.rewater.model.status.DetailedStatusModel;
import michael.linker.rewater.ui.view.IView;

public class DetailStatusView implements IView {
    private final View mView;
    private final ImageView waterStatusIcon;
    private final ImageView energyStatusIcon;

    public DetailStatusView(final View view) {
        mView = view;
        waterStatusIcon = view.findViewById(
                R.id.water_detailed_status_image);
        energyStatusIcon = view.findViewById(
                R.id.battery_detailed_status_image);
    }

    public void setStatus(final DetailedStatusModel detailedStatusModel) {
        waterStatusIcon.setImageTintList(ColorStateList.valueOf(
                StatusColorProvider.getColorForStatus(detailedStatusModel.getWater())));
        energyStatusIcon.setImageDrawable(
                StatusDrawablesProvider.getIconForEnergyStatus(detailedStatusModel.getEnergy()));
    }

    @Override
    public View getViewInstance() {
        return mView;
    }

}
