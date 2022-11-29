package michael.linker.rewater.data.model;

/**
 * General model for the "id" and "name" fields
 */
public final class IdNameModel {
    private final String mId;
    private final String mName;

    public IdNameModel(final String id, final String name) {
        mId = id;
        mName = name;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }
}
