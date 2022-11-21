package michael.linker.rewater.data.model.unit;

import michael.linker.rewater.R;
import michael.linker.rewater.data.res.StringsProvider;

public class WaterVolumeMetricModel implements IUnit {
    private static final int MIN_WATERING_VOLUME_IN_ML = 10;
    private static final String SPACE = " ";
    private final Integer mLitres, mMillilitres;

    public WaterVolumeMetricModel(final Integer litres, final Integer millilitres) {
        mLitres = litres;
        mMillilitres = millilitres;
    }

    public Integer getLitres() {
        return mLitres;
    }

    public Integer getMillilitres() {
        return mMillilitres;
    }

    @Override
    public String formatToCompact() {
        return mLitres + StringsProvider.getString(R.string.unit_litres_short) + SPACE
                + mMillilitres + StringsProvider.getString(R.string.unit_millilitres_short);
    }

    @Override
    public boolean isDataCorrect() {
        final int wateringVolumeMl = (mLitres * 1000) + mMillilitres;
        return wateringVolumeMl < MIN_WATERING_VOLUME_IN_ML;
    }
}
