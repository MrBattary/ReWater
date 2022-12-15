package michael.linker.rewater.data.web.api.common.part;

import michael.linker.rewater.data.model.unit.WaterVolumeMetricModel;

public class VolumePart {
    private final Integer l;
    private final Integer ml;

    public VolumePart(Integer l, Integer ml) {
        this.l = l;
        this.ml = ml;
    }

    public VolumePart(WaterVolumeMetricModel model) {
        l = model.getLitres();
        ml = model.getMillilitres();
    }

    public Integer getL() {
        return l;
    }

    public Integer getMl() {
        return ml;
    }
}
