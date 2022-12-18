package michael.linker.rewater.data.model.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import michael.linker.rewater.data.web.api.common.part.StatusPart;

public class DetailedStatusModel {
    private final Status mWater;
    private final Status mBattery;

    public DetailedStatusModel(final Status water, final Status battery) {
        this.mWater = water;
        this.mBattery = battery;
    }

    public DetailedStatusModel(final StatusPart statusPart) {
        if (statusPart == null) {
            mWater = Status.UNDEFINED;
            mBattery = Status.UNDEFINED;
        } else {
            mWater = Status.valueOf(statusPart.getWater());
            mBattery = Status.valueOf(statusPart.getBattery());
        }
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
