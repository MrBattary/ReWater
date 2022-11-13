package michael.linker.rewater.data.repository.devices.model;

public class EditableDeviceModel {
    private final String mName;

    public EditableDeviceModel(
            final String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }
}
