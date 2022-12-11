package michael.linker.rewater.data.web.api.common.request;

public class PageSizeCommonRequest {
    private final int page, size;

    public PageSizeCommonRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}
