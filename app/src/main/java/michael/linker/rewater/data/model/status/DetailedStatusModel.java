package michael.linker.rewater.data.model.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailedStatusModel {
    private final Status mWater;
    private final Status mBattery;

    public DetailedStatusModel(final Status water, final Status battery) {
        this.mWater = water;
        this.mBattery = battery;
    }

    public Status getWater() {
        return mWater;
    }

    public Status getBattery() {
        return mBattery;
    }

    public List<Status> toList() {
        return new ArrayList<>(Arrays.asList(mWater, mBattery));
    }
}
