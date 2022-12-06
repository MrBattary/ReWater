package michael.linker.rewater.data.web.gate;

public class HttpResponseStatus {
    public static final HttpResponseStatus OK;
    public static final HttpResponseStatus BAD_REQUEST;
    public static final HttpResponseStatus FORBIDDEN;
    public static final HttpResponseStatus NOT_FOUND;
    public static final HttpResponseStatus SERVER_ERROR;

    public static final int OK_CODE = 200;
    public static final int BAD_REQUEST_CODE = 400;
    public static final int FORBIDDEN_CODE = 403;
    public static final int NOT_FOUND_CODE = 404;
    public static final int SERVER_ERROR_CODE = 500;

    public static final String OK_DESC = "OK";
    public static final String BAD_REQUEST_DESC = "BAD_REQUEST";
    public static final String FORBIDDEN_DESC = "FORBIDDEN";
    public static final String NOT_FOUND_DESC = "NOT_FOUND";
    public static final String SERVER_ERROR_DESC = "SERVER_ERROR";
    public static final String UNRECOGNIZED_DESC = "UNRECOGNIZED";

    static {
        OK = new HttpResponseStatus(OK_CODE, OK_DESC);
        BAD_REQUEST = new HttpResponseStatus(BAD_REQUEST_CODE, BAD_REQUEST_DESC);
        FORBIDDEN = new HttpResponseStatus(FORBIDDEN_CODE, FORBIDDEN_DESC);
        NOT_FOUND = new HttpResponseStatus(NOT_FOUND_CODE, NOT_FOUND_DESC);
        SERVER_ERROR = new HttpResponseStatus(SERVER_ERROR_CODE, SERVER_ERROR_DESC);
    }

    private final int code;
    private final String description;

    private HttpResponseStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public static HttpResponseStatus recognizeStatusByCode(final int code) {
        switch (code) {
            case OK_CODE:
                return OK;
            case BAD_REQUEST_CODE:
                return BAD_REQUEST;
            case FORBIDDEN_CODE:
                return FORBIDDEN;
            case NOT_FOUND_CODE:
                return NOT_FOUND;
            case SERVER_ERROR_CODE:
                return SERVER_ERROR;
            default:
                return new HttpResponseStatus(code, UNRECOGNIZED_DESC);
        }
    }
}
