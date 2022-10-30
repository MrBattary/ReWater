package michael.linker.rewater.ui.view;

import android.content.res.ColorStateList;
import android.graphics.drawable.VectorDrawable;
import android.view.View;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

import michael.linker.rewater.R;
import michael.linker.rewater.model.status.DetailedStatusModel;
import michael.linker.rewater.assist.provider.ColorProvider;
import michael.linker.rewater.assist.provider.DrawablesProvider;
import michael.linker.rewater.constant.Status;

public class DetailStatusView implements IView {
    private final View mView;
    private final ImageView waterStatusIcon;
    private final ImageView energyStatusIcon;
    private final Map<Status, Integer> waterStatusColorMap;
    private final Map<Status, VectorDrawable> energyStatusIconMap;

    public DetailStatusView(final View view) {
        mView = view;
        waterStatusColorMap = initWaterStatusColorMap();
        energyStatusIconMap = initBatteryStatusIconMap();
        waterStatusIcon = view.findViewById(
                R.id.water_detailed_status_image);
        energyStatusIcon = view.findViewById(
                R.id.battery_detailed_status_image);
    }

    public void setStatus(final DetailedStatusModel detailedStatusModel) {
        waterStatusIcon.setImageTintList(
                ColorStateList.valueOf(waterStatusColorMap.get(detailedStatusModel.getWater())));
        energyStatusIcon.setImageDrawable(energyStatusIconMap.get(detailedStatusModel.getEnergy()));
    }

    @Override
    public View getViewInstance() {
        return mView;
    }

    private Map<Status, Integer> initWaterStatusColorMap() {
        return ColorProvider.getStatusColorMap();
    }

    private Map<Status, VectorDrawable> initBatteryStatusIconMap() {
        Map<Status, VectorDrawable> initMap = new HashMap<>();
        initMap.put(Status.OK,
                DrawablesProvider.getVectorDrawable(R.drawable.ic_battery_fine));
        initMap.put(Status.WARNING,
                DrawablesProvider.getVectorDrawable(R.drawable.ic_battery_warning));
        initMap.put(Status.DEFECT,
                DrawablesProvider.getVectorDrawable(R.drawable.ic_battery_defect));
        return initMap;
    }
}
