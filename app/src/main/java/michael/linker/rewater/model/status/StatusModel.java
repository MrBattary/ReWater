package michael.linker.rewater.model.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import michael.linker.rewater.constant.Status;

public class StatusModel {
    private final Status water;
    private final Status energy;

    public StatusModel(final Status water, final Status energy) {
        this.water = water;
        this.energy = energy;
    }

    public Status getWater() {
        return water;
    }

    public Status getEnergy() {
        return energy;
    }

    public List<Status> toList() {
        return new ArrayList<>(Arrays.asList(water, energy));
    }
}
