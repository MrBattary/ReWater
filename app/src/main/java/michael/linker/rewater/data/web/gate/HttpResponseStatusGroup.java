package michael.linker.rewater.data.web.gate;

public class HttpResponseStatusGroup {
    public static final HttpResponseStatusGroup INFO;
    public static final HttpResponseStatusGroup SUCCESS;
    public static final HttpResponseStatusGroup REDIRECT;
    public static final HttpResponseStatusGroup CLIENT_ERROR;
    public static final HttpResponseStatusGroup SERVER_ERROR;

    static {
        INFO = new HttpResponseStatusGroup(100, 199);
        SUCCESS = new HttpResponseStatusGroup(200, 299);
        REDIRECT = new HttpResponseStatusGroup(300, 399);
        CLIENT_ERROR = new HttpResponseStatusGroup(400, 499);
        SERVER_ERROR = new HttpResponseStatusGroup(500, 599);
    }

    private final int lowerCode, higherCode;

    public HttpResponseStatusGroup(int lowerCode, int higherCode) {
        this.lowerCode = lowerCode;
        this.higherCode = higherCode;
    }


    public static boolean isStatusSuccess(HttpResponseStatus status) {
        return isStatusBelongsToGroup(status, SUCCESS);
    }

    public static boolean isStatusClientError(HttpResponseStatus status) {
        return isStatusBelongsToGroup(status, CLIENT_ERROR);
    }

    public static boolean isStatusServerError(HttpResponseStatus status) {
        return isStatusBelongsToGroup(status, SERVER_ERROR);
    }

    public static boolean isStatusBelongsToGroup(HttpResponseStatus status,
            HttpResponseStatusGroup group) {
        return group.lowerCode <= status.getCode() && status.getCode() <= group.higherCode;
    }
}
