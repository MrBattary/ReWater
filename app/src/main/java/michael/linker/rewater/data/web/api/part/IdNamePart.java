package michael.linker.rewater.data.web.api.part;

public class IdNamePart {
    private final String id;
    private final String name;

    public IdNamePart(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
