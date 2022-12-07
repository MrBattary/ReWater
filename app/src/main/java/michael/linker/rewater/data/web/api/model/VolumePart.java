package michael.linker.rewater.data.web.api.model;

public class VolumePart {
    private final Integer l;
    private final Integer ml;

    public VolumePart(Integer l, Integer ml) {
        this.l = l;
        this.ml = ml;
    }

    public Integer getL() {
        return l;
    }

    public Integer getMl() {
        return ml;
    }
}
