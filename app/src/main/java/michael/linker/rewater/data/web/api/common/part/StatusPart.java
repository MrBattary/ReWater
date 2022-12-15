package michael.linker.rewater.data.web.api.common.part;

public class StatusPart {
    private final String water;
    private final String battery;

    public StatusPart(String water, String battery) {
        this.water = water;
        this.battery = battery;
    }

    public String getWater() {
        return water;
    }

    public String getBattery() {
        return battery;
    }
}
