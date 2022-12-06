package michael.linker.rewater.data.web.api.model;

public class StatusResponsePart {
    private final String water;
    private final String battery;

    public StatusResponsePart(String water, String battery) {
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
