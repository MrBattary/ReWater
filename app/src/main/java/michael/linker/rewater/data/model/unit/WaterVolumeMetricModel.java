package michael.linker.rewater.data.model.unit;

import michael.linker.rewater.R;
import michael.linker.rewater.data.res.StringsProvider;

public class WaterVolumeMetricModel implements IUnit {
    private static final String SPACE = " ";
    private final int mLitres, mMillilitres;

    public WaterVolumeMetricModel(final int litres, final int millilitres) {
        mLitres = litres;
        mMillilitres = millilitres;
    }

    public int getLitres() {
        return mLitres;
    }

    public int getMillilitres() {
        return mMillilitres;
    }

    @Override
    public String formatToCompact() {
        return mLitres + SPACE + StringsProvider.getString(R.string.unit_litres_short) + SPACE + mMillilitres + SPACE + StringsProvider.getString(R.string.unit_millilitres_short);
    }
}
