package michael.linker.rewater.data.model.unit;

import michael.linker.rewater.R;
import michael.linker.rewater.data.res.StringsProvider;

public class WaterVolumeModel implements IUnit {
    private static final String SPACE = " ";
    private final int mLiters, mMilliliters;

    public WaterVolumeModel(final int liters, final int milliliters) {
        mLiters = liters;
        mMilliliters = milliliters;
    }

    @Override
    public String formatToCompact() {
        return mLiters + SPACE + StringsProvider.getString(R.string.unit_litres_short) + SPACE +
                mMilliliters + SPACE + StringsProvider.getString(R.string.unit_millilitres_short)
                + SPACE;
    }
}
