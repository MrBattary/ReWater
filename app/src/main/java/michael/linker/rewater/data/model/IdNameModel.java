package michael.linker.rewater.data.model;

import michael.linker.rewater.data.web.api.common.part.IdNamePart;

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

    public IdNameModel(IdNamePart responsePart) {
        mId = responsePart.getId();
        mName = responsePart.getName();
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }
}
