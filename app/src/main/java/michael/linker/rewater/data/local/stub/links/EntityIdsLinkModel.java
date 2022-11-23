package michael.linker.rewater.data.local.stub.links;

public class EntityIdsLinkModel {
    private final String firstEntityId, secondEntityId;

    public EntityIdsLinkModel(final String firstEntityId, final String secondEntityId) {
        this.firstEntityId = firstEntityId;
        this.secondEntityId = secondEntityId;
    }

    public String getFirstEntityId() {
        return firstEntityId;
    }

    public String getSecondEntityId() {
        return secondEntityId;
    }
}
